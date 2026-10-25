import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { execSync } from 'child_process';

// Xác định __dirname
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Lấy thông tin tác giả và ngày sửa đổi từ lịch sử commit của Git
const getGitInfo = (filePath) => {
    try {
        // Bao bọc đường dẫn file trong dấu ngoặc kép để xử lý các ký tự đặc biệt
        const authorsCommand = `git log --follow --pretty=format:"%an" "${filePath}"`;
        const datesCommand = `git log --follow --pretty=format:"%ai" "${filePath}"`;

        const authors = execSync(authorsCommand, { encoding: 'utf8' }).trim().split('\n');
        const dates = execSync(datesCommand, { encoding: 'utf8' }).trim().split('\n');

        if (authors.length === 0 || dates.length === 0) return { authors: [], authorDates: {}, date: new Date().toISOString().split('T')[0] };

        // Lưu ngày của từng tác giả
        const authorDates = authors.reduce((acc, author, index) => {
            if (!acc[author]) acc[author] = [];
            acc[author].push(new Date(dates[index]));
            return acc;
        }, {});

        // Chọn mốc thời gian gần nhất từ tất cả các ngày của các tác giả
        const allDates = dates.map(date => new Date(date));
        const latestDate = new Date(Math.max(...allDates));
        const formattedDate = latestDate.toISOString().split('T')[0];

        return { authors: Object.keys(authorDates), authorDates, date: formattedDate };
    } catch (error) {
        return { authors: [], authorDates: {}, date: new Date().toISOString().split('T')[0] };
    }
};

const generateHeader = (authors, authorDates, date, description = '', isHtml = false) => {
    const tab = ' '; // Ký tự tab hoặc khoảng trắng

    const authorDetails = authors.map(author => {
        const latestDate = new Date(Math.max(...authorDates[author].map(d => new Date(d))));
        return `${tab}${author}: ${latestDate.toISOString().split('T')[0]}`;
    }).join('\n');

    if (isHtml) {
        return `<!--
${tab}@file
${authorDetails}
${tab}Description: ${description}
${tab}Date: ${date}
${tab}@header-script
-->`;
    }

    return `/**
${tab}* @file
${authorDetails}
${tab}* Description: ${description}
${tab}* Date: ${date}
${tab}@header-script
*/`;
};

const updateHeader = (filePath, newHeader) => {
    if (fs.existsSync(filePath)) {
        const content = fs.readFileSync(filePath, 'utf8');
        const extname = path.extname(filePath);
        const isHtml = extname === '.html' || extname === '.vue';

        // Regex để tìm comment header cũ
        const headerPattern = isHtml
            ? /<!--[\s\S]*?@header-script[\s\S]*?-->/
            : /\/\*\*[\s\S]*?\* @header-script[\s\S]*?\*\//;

        // Tìm comment header cũ
        const headerMatch = content.match(headerPattern);

        // Xóa comment header cũ và thêm comment header mới
        const updatedContent = headerMatch
            ? content.replace(headerPattern, newHeader)
            : `${newHeader}\n\n${content}`;

        fs.writeFileSync(filePath, updatedContent);
    }
};

const updateHeaders = (dir) => {
    const items = fs.readdirSync(dir);

    items.forEach(item => {
        const fullPath = path.join(dir, item);
        if (fs.statSync(fullPath).isDirectory()) {
            updateHeaders(fullPath); // Recursively update headers in subdirectories
        } else if (fullPath.endsWith('.js') || fullPath.endsWith('.vue') || fullPath.endsWith('.css') || fullPath.endsWith('.scss') || fullPath.endsWith('.html')) {
            const { authors, authorDates, date } = getGitInfo(fullPath);
            const extname = path.extname(fullPath);
            const isHtml = extname === '.html' || extname === '.vue';
            const header = generateHeader(authors, authorDates, date, '', isHtml);
            updateHeader(fullPath, header);
        }
    });
};

// Gọi script với thư mục bạn muốn xử lý
updateHeaders(path.join(__dirname, 'src'));  // Thay '/path/to/your/project' bằng đường dẫn đến thư mục gốc dự án của bạn