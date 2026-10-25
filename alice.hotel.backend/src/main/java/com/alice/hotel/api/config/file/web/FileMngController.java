package com.alice.hotel.api.config.file.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.config.file.service.FileMngService;
import com.alice.hotel.api.config.security.handler.CustomException;
import com.alice.hotel.api.dto.common.fileMng.FileDto;
import com.alice.hotel.api.dto.common.fileMng.FileMngDto;
import com.alice.hotel.api.dto.common.fileMng.FileUploadedInfoDto;
import com.alice.hotel.api.dto.common.fileMng.MultipleFileDTO;
import com.alice.hotel.api.dto.common.fileMng.SingleFileDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping (value = {"/cmm/files"})
@RequiredArgsConstructor
public class FileMngController {

    // List ALLOWED EXTENSIONS of file
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".hwp", ".hwpx", ".doc", ".docx", ".xls",
            ".xlsx", ".ppt", ".pptx", ".txt", ".pdf", ".zip", ".jpg", ".png", ".jpeg", ".mp4");

    private final FileMngService fileMngService;

    @Value ("${upload.path}")
    private String uploadPath;

    private Path fileStorageLocation;

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    /**
     * Upload file
     *
     * @param files       .
     * @param category    .
     * @param orgName     .
     * @param sectionName .
     * @param referKeyId  .
     * @return FileMngDto
     * @throws IOException .
     */
    @PostMapping (value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<List<FileMngDto>> uploadFile(@RequestParam ("files") MultipartFile[] files,
            @RequestParam ("category") String category, @RequestParam ("orgName") String orgName,
            @RequestParam ("sectionName") String sectionName, @RequestParam ("referKeyId") String referKeyId) throws
            IOException {

        if (files.length == 1 && files[ 0 ].getOriginalFilename().isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        List<FileMngDto> listFileMngDto = new ArrayList<FileMngDto>();
        List<FileMngDto> fileRes = new ArrayList<FileMngDto>();

        for (MultipartFile file : files) {

            String fileExtension = getFileExtension(file.getOriginalFilename());
            if (! ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EXTENSIONS);
            }

            FileMngDto fileMngDto = new FileMngDto();
            fileMngDto = fileMngService.uploadFile(file, category, orgName, sectionName);
            fileMngDto.setFimReferKeyId(referKeyId);
            listFileMngDto.add(fileMngDto);
            fileRes = fileMngService.createNewFile(listFileMngDto);
        }

        return new ApiResponse<>(ApiStatus.CREATED, fileRes);
    }

    /**
     * Upload file with editor
     *
     * @param file        .
     * @param category    .
     * @param orgName     .
     * @param sectionName .
     * @return FileUploadedInfoDto
     * @throws IOException .
     */
    @PostMapping (value = "uploadEditer", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<FileUploadedInfoDto> uploadEditer(@RequestParam ("file") MultipartFile file,
            @RequestParam ("category") String category, @RequestParam ("orgName") String orgName,
            @RequestParam ("sectionName") String sectionName) throws IOException {

        if (file == null) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        FileMngDto fileMngDto = fileMngService.uploadFile(file, category, orgName, sectionName);
        FileUploadedInfoDto fileUploadedInfoDto = fileMngService.createEditNewFile(fileMngDto);

        return new ApiResponse<>(ApiStatus.CREATED, fileUploadedInfoDto);
    }

    /**
     * Download single file
     *
     * @body SingleFileDTO .
     * @return ResponseEntity<Resource> .
     * @throws IOException .
     */
    @PostMapping ("/download")
    public ResponseEntity<Resource> downloadFile(@RequestBody SingleFileDTO singleFileDTO) throws
            IOException {

        if (singleFileDTO.getEncodedFileName().isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        Resource resource = fileMngService.prepareDownloadSingleFile(singleFileDTO.getEncodedFileName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentLength(resource.getFile().length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * Download multiple file
     *
     * @param multipleFileDTO .
     * @return ResponseEntity<Resource>
     */
    @PostMapping ("/downloadMultiple")
    public ResponseEntity<Resource> downloadFiles(@RequestBody MultipleFileDTO multipleFileDTO) {
        try {
            List<String> referKeys = multipleFileDTO.getFimReferKeyIdList();
            List<String> sectionNames = multipleFileDTO.getFimSectionName();
            Resource resource = fileMngService.prepareDownloadFiles(referKeys, sectionNames);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentLength(resource.getFile().length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            throw new CustomException(ApiStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a list of file information
     *
     * @return List<FileUploadedInfoDto>
     * @throws IOException .
     */
    @PostMapping ("/fileUploadedInfo")
    public ApiResponse<List<FileUploadedInfoDto>> getFileUploadedInfo(@RequestBody FileDto fileDto) throws IOException {

        List<FileUploadedInfoDto> fileUploadedInfoDtos = fileMngService.selectFileUploaded(fileDto);
        return new ApiResponse<>(ApiStatus.SUCCESS, fileUploadedInfoDtos);
    }

    /**
     * Delete file
     *
     * @param encodedFileName .
     * @return String
     */
    @PutMapping ("/fileMng/{encodedFileName}")
    public ApiResponse<String> deleteUploadedFile(@PathVariable String encodedFileName) {
        try {
            String deletedFileId = fileMngService.deleteUploadedFile(encodedFileName);

            return new ApiResponse<>(ApiStatus.SUCCESS, encodedFileName);
        } catch (FileNotFoundException e) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new CustomException(ApiStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
