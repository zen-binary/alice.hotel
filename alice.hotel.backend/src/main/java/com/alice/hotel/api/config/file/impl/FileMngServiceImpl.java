package com.alice.hotel.api.config.file.impl;

import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.config.file.service.FileMngService;
import com.alice.hotel.api.config.security.handler.CustomException;
import com.alice.hotel.api.config.security.util.SequenceGenerator;
import com.alice.hotel.api.dto.common.fileMng.FileDto;
import com.alice.hotel.api.dto.common.fileMng.FileMngDto;
import com.alice.hotel.api.dto.common.fileMng.FileUploadedInfoDto;
import com.alice.hotel.api.entity.common.FileMngEntity;
import com.alice.hotel.api.repository.common.FileMngRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.alice.hotel.api.common.GlobalEnum.*;

@Service
@RequiredArgsConstructor
public class FileMngServiceImpl implements FileMngService {

    private final ModelMapper modelMapper;

    private final FileMngRepository fileMngRepository;

    @Value ("${upload.path}")
    private String uploadPath;

    @Value ("${download.path}")
    private String downloadPath;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    /**
     * Encode file name upload
     *
     * @param originalName .
     * @return String encodeFileName
     */
    private String encodeFileName(String originalName) {
        String uniqueString = UUID.randomUUID() + originalName;
        return DigestUtils.sha256Hex(uniqueString);
    }

    /**
     * upload file
     *
     * @param file        .
     * @param folderName  .
     * @param orgName     .
     * @param sectionName .
     * @return FileMngDto
     * @throws IOException .
     */
    public FileMngDto uploadFile(MultipartFile file, String folderName, String orgName, String sectionName) throws
            IOException {

        FileMngDto fileMngDto = new FileMngDto();
        String originalName = file.getOriginalFilename();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String[] fileExt = originalName.split("\\.");
        String uploadDirectory = uploadPath + '/' + folderName + '/' + dateFormat.format(currentDate);
        String encodedFileNameWithoutExt = encodeFileName(originalName);
        String encodedFileName = encodedFileNameWithoutExt + "." + fileExt[ fileExt.length - 1 ];


        Path filePath = Paths.get(uploadDirectory);
        if (! Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        Path filePathSave = filePath.resolve(encodedFileName);
        Files.copy(file.getInputStream(), filePathSave);

        // setter fileMngDto
        fileMngDto.setFimId(sequenceGenerator.getNextSequence(PREFIX_FILE_MNG.toString(), TABLE_FILE_MNG.toString()));
        fileMngDto.setFimFileCategory(folderName);
        fileMngDto.setFimFileName(encodedFileNameWithoutExt);
        fileMngDto.setFimFilePath(uploadDirectory);
        fileMngDto.setFimFileExt(fileExt[ fileExt.length - 1 ]);
        fileMngDto.setFimFileSize(file.getSize());
        fileMngDto.setFimUseYn(STATE_Y.toString());
        fileMngDto.setFimFileOrgName(originalName);
        fileMngDto.setFimSectionName(sectionName);
        fileMngDto.setDelYn(STATE_N.toString());

        return fileMngDto;
    }

    /**
     * Create new file
     *
     * @param fileMngDtoList .
     * @return List<FileMngDto>
     */
    @Transactional
    public List<FileMngDto> createNewFile(List<FileMngDto> fileMngDtoList) {
        List<FileMngEntity> fileMngEntities = fileMngDtoList.stream()
                .map(dto -> modelMapper.map(dto, FileMngEntity.class)).collect(Collectors.toList());

        fileMngEntities = fileMngRepository.saveAll(fileMngEntities);

        return fileMngEntities.stream().map(entity -> modelMapper.map(entity, FileMngDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Create new file with editor
     *
     * @param fileMngDto .
     * @return FileUploadedInfoDto
     */
    @Transactional
    public FileUploadedInfoDto createEditNewFile(FileMngDto fileMngDto) {

        FileMngEntity fileMngEntity = modelMapper.map(fileMngDto, FileMngEntity.class);
        FileMngEntity fileResult = fileMngRepository.save(fileMngEntity);

        FileUploadedInfoDto fileUploadedInfoDto = new FileUploadedInfoDto();
        fileUploadedInfoDto.setFimId(
                sequenceGenerator.getNextSequence(PREFIX_FILE_MNG.toString(), TABLE_FILE_MNG.toString()));

        String newPath = fileResult.getFimFilePath().replace(uploadPath, "files");
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + newPath + "/")
                .path(fileResult.getFimFileName() + "." + fileMngEntity.getFimFileExt()).toUriString();

        fileUploadedInfoDto.setUrlFile(fileUrl);

        return fileUploadedInfoDto;
    }

    /**
     * Get a list of file information
     *
     * @return List<FileUploadedInfoDto>
     */
    public List<FileUploadedInfoDto> selectFileUploaded(FileDto fileDto) {

        List<FileUploadedInfoDto> fileUploadedInfoDtos = new ArrayList<FileUploadedInfoDto>();
        List<FileMngEntity> fileMngEntities = fileMngRepository.findByFimReferKeyIdAndFimFileCategoryAndFimSectionNameAndDelYn(
                fileDto.getFimReferKeyId(), fileDto.getFimFileCategory(), fileDto.getFimSectionName(), STATE_N.toString());

        FileUploadedInfoDto dto;
        String fileUrl;
        for (FileMngEntity fileMngEntity : fileMngEntities) {
            dto = new FileUploadedInfoDto();
            dto.setFimFileName(fileMngEntity.getFimFileName());
            dto.setFimOrgName(fileMngEntity.getFimFileOrgName());
            dto.setFimId(fileMngEntity.getFimId());
            dto.setFimFilePath(fileMngEntity.getFimFilePath());
            dto.setFimFileSize(fileMngEntity.getFimFileSize());
            String newPath = fileMngEntity.getFimFilePath().replace(uploadPath, "files");
            fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + newPath + "/")
                    .path(fileMngEntity.getFimFileName() + "." + fileMngEntity.getFimFileExt()).toUriString();
            dto.setUrlFile(fileUrl);
            fileUploadedInfoDtos.add(dto);
        }

        return fileUploadedInfoDtos;
    }

    /**
     * Download single file
     *
     * @param encodedFileName .
     * @return Resource
     * @throws IOException .
     */
    public Resource prepareDownloadSingleFile(String encodedFileName) throws IOException {
        FileMngDto fileMngDto = getFilesEncodedFileName(encodedFileName);

        if (fileMngDto == null) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        List<FileMngDto> fileMngDtos = new ArrayList<>();
        fileMngDtos.add(fileMngDto);

        return zipAndPrepareDownload(fileMngDtos);
    }

    /**
     * Download multiple file
     *
     * @param referKeys    .
     * @param sectionNames .
     * @return Resource
     * @throws IOException .
     */
    public Resource prepareDownloadFiles(List<String> referKeys, List<String> sectionNames) throws IOException {
        List<FileMngDto> fileMngDtos = getFilesByReferKeyAndSectionName(referKeys, sectionNames);

        if (fileMngDtos.isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        return zipAndPrepareDownload(fileMngDtos);
    }

    /**
     * Process zip file and Prepare Download
     *
     * @param fileMngDtos .
     * @return Resource
     * @throws IOException .
     */
    private Resource zipAndPrepareDownload(List<FileMngDto> fileMngDtos) throws IOException {

        String tempDirectoryPath = createTempDirectory();
        List<Resource> resources = new ArrayList<>();

        for (FileMngDto fileMngDto : fileMngDtos) {
            String orgFileName = fileMngDto.getFimFileOrgName();
            String encodedFileName = fileMngDto.getFimFileName();
            String fileExt = fileMngDto.getFimFileExt();
            String uploadDirectory = fileMngDto.getFimFilePath();
            Path filePath = Paths.get(uploadDirectory).resolve(encodedFileName + "." + fileExt);

            if (Files.exists(filePath)) {
                Path tempFilePath = Paths.get(tempDirectoryPath).resolve(orgFileName);
                Files.copy(filePath, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
                resources.add(new FileSystemResource(tempFilePath.toFile()));
            } else {
                throw new CustomException(ApiStatus.BAD_REQUEST_FILE_NOT_READ);
            }
        }

        return createZipFile(resources, "DownloadFiles", tempDirectoryPath);
    }

    /**
     * Create zip file
     *
     * @param resources         .
     * @param zipFileName       .
     * @param tempDirectoryPath .
     * @return Resource
     * @throws IOException .
     */
    public Resource createZipFile(List<Resource> resources, String zipFileName, String tempDirectoryPath) throws
            IOException {

        String zipFilePath = tempDirectoryPath + "/" + zipFileName + ".zip";
        Path zipFile = Paths.get(zipFilePath);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            for (Resource resource : resources) {
                String relativePath = resource.getFilename();
                ZipEntry zipEntry = new ZipEntry(relativePath);
                zipOutputStream.putNextEntry(zipEntry);
                StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
        }

        return new FileSystemResource(zipFile.toFile());
    }

    /**
     * getFilesEncodedFileName
     *
     * @param encodedFileName .
     * @return FileMngDto
     */
    public FileMngDto getFilesEncodedFileName(String encodedFileName) {

        FileMngEntity entity = fileMngRepository.findByFimFileNameAndDelYn(encodedFileName, STATE_N.toString());

        return modelMapper.map(entity, FileMngDto.class);
    }

    /**
     * get Files By ReferKey And SectionName
     *
     * @param referKeys    .
     * @param sectionNames .
     * @return List<FileMngDto>
     */
    public List<FileMngDto> getFilesByReferKeyAndSectionName(List<String> referKeys, List<String> sectionNames) {

        List<FileMngEntity> entities = fileMngRepository.findByFimReferKeyIdInAndFimSectionNameInAndDelYn(
                referKeys, sectionNames, STATE_N.toString());

        return entities.stream().map(entity -> modelMapper.map(entity, FileMngDto.class)).collect(Collectors.toList());
    }

    /**
     * Create temp download directory
     *
     * @return String.
     * @throws IOException .
     */
    private String createTempDirectory() throws IOException {

        String downloadFolder = downloadPath;
        Path filePath = Paths.get(downloadFolder);

        if (! Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        String tempDirectoryPath = downloadPath + "/temp_" + UUID.randomUUID();
        Files.createDirectory(Paths.get(tempDirectoryPath));

        return tempDirectoryPath;
    }

    /**
     * Delete download directory
     *
     * @throws IOException .
     */
    @Transactional
    public void deleteDownloadFolders() throws IOException {
        Path downloadFolder = Paths.get(downloadPath);
        Files.walk(downloadFolder).sorted((a, b) -> - a.compareTo(b)).map(Path::toFile).forEach(File::delete);
    }

    /**
     * Delete upload file
     *
     * @param encodedFileName .
     * @return String
     * @throws IOException .
     */
    @Transactional
    public String deleteUploadedFile(String encodedFileName) throws IOException {

        FileMngEntity entity = fileMngRepository.findByFimFileNameAndDelYn(encodedFileName, STATE_N.toString());

        if (entity != null) {
            Path filePath = Paths.get(entity.getFimFilePath(), entity.getFimFileName() + "." + entity.getFimFileExt());
            Files.deleteIfExists(filePath);
            entity.setDelYn(STATE_Y.toString());
            fileMngRepository.save(entity);
            return entity.getFimId();
        } else {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }
    }

}
