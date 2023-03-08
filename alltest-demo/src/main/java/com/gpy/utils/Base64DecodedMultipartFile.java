package com.gpy.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;

    private final String header;

    public Base64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    /**
     * Return the name of the parameter in the multipart form.
     *
     * @return the name of the parameter (never {@code null} or empty)
     */
    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    /**
     * Return the original filename in the client's filesystem.
     * <p>This may contain path information depending on the browser used,
     * but it typically will not with any other than Opera.
     *
     * @return the original filename, or the empty String if no file
     * has been chosen in the multipart form, or {@code null}
     * if not defined or not available
     */
    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + header.split("/")[1];
    }

    /**
     * Return the content type of the file.
     *
     * @return the content type, or {@code null} if not defined
     * (or no file has been chosen in the multipart form)
     */
    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    /**
     * Return whether the uploaded file is empty, that is, either no file has
     * been chosen in the multipart form or the chosen file has no content.
     */
    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    /**
     * Return the size of the file in bytes.
     *
     * @return the size of the file, or 0 if empty
     */
    @Override
    public long getSize() {
        return imgContent.length;
    }

    /**
     * Return the contents of the file as an array of bytes.
     *
     * @return the contents of the file as bytes, or an empty byte array if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    /**
     * Return an InputStream to read the contents of the file from.
     * The user is responsible for closing the stream.
     *
     * @return the contents of the file as stream, or an empty stream if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    /**
     * Transfer the received file to the given destination file.
     * <p>This may either move the file in the filesystem, copy the file in the
     * filesystem, or save memory-held contents to the destination file.
     * If the destination file already exists, it will be deleted first.
     * <p>If the file has been moved in the filesystem, this operation cannot
     * be invoked again. Therefore, call this method just once to be able to
     * work with any storage mechanism.
     *
     * @param dest the destination file
     * @throws IOException           in case of reading or writing errors
     * @throws IllegalStateException if the file has already been moved
     *                               in the filesystem and is not available anymore for another transfer
     */
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}
