package com.winwintek.web.backend.api.config.file.service;


import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileMngDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileMngService {

    /**
     * Upload file
     */
    FileMngDto uploadFile (MultipartFile file, String folderName, String orgName, String sectionName) throws
            IOException;

    /**
     * Download single file
     */
    Resource prepareDownloadSingleFile (String encodedFileName) throws IOException;

    /**
     * Create Zip file
     */
    Resource createZipFile (List<Resource> resources, String zipFileName, String tempDirectoryPath) throws IOException;

    /**
     * Create new File
     */
    List<FileMngDto> createNewFile (List<FileMngDto> fileMngDtoList);

    /**
     * Create new file with editor
     */
    FileUploadedInfoDto createEditNewFile (FileMngDto fileMngDto);

    /**
     * Get a list of file information
     */
    List<FileUploadedInfoDto> selectFileUploaded (FileDto fileDto);

    /**
     * Download multiple file
     */
    Resource prepareDownloadFiles (List<String> referKeys, List<String> sectionNames) throws IOException;

    /**
     * Delete download directory
     */
    void deleteDownloadFolders () throws IOException;

    /**
     * Delete file
     */
    String deleteUploadedFile (String encodedFileName) throws IOException;

}
