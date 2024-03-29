package it.fabio.libreria.service;

import it.fabio.libreria.entity.Image;
import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.repository.ImageRepository;
import it.fabio.libreria.repository.LibroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class ImageService {


    private final ImageRepository imageRepository;

    private final LibroRepository libroRepository;

    public ResponseEntity<?> checkImage(long size, int width, int height, String[] extensions, MultipartFile file){
        if(checkSize(file, size))
            return new ResponseEntity("File too large", HttpStatus.BAD_REQUEST);

        if(checkDimensions(width, height, file))
            return new ResponseEntity("Wrong file dimensions", HttpStatus.BAD_REQUEST);

        if(checkExtensions(file, extensions))
            return new ResponseEntity("File type non allowed", HttpStatus.BAD_REQUEST);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addImageToContent(long size, int width, int height, String[] extensions, MultipartFile file, long libroId) throws IOException {
        Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new ResourceNotFoundException("Libro", "id", libroId));
        if (checkImage(size, width, height, extensions, file).getStatusCode().equals(HttpStatus.OK)) {
            // Converti il file direttamente in Base64
            String imageData = convertFileToBase64(file);
            Image i = new Image();
            i.setImage(imageData);
            imageRepository.save(i);
            libro.setImage(i);
            return new ResponseEntity("Image added to content", HttpStatus.CREATED);
        }
        return new ResponseEntity("Something went wrong adding image to content", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String convertFileToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private boolean checkSize(MultipartFile file, long size){
        if(file.getSize() > size || file.isEmpty())
            return true;
        return false;
    }

    private BufferedImage fromMultipartFileToBufferedImage(MultipartFile file){
        BufferedImage bf = null;
        try{
            bf = ImageIO.read(file.getInputStream());
            return bf;
        } catch (IOException e){
            return null;
        }
    }

    private boolean checkDimensions(int width, int height, MultipartFile file){
        BufferedImage bf = fromMultipartFileToBufferedImage(file);
        if(bf != null){
            if(bf.getWidth() > width || bf.getHeight() > height)
                return true;
        }
        return false;
    }

    private boolean checkExtensions(MultipartFile file, String[] extensions){
        String filename = file.getOriginalFilename(); // filename = pippo.jpeg.png
        String ext = null;
        try{
            ext = filename.substring(filename.lastIndexOf(".")+1);
            if(Arrays.stream(extensions).anyMatch(ext::equalsIgnoreCase))
                return false;
        } catch(NullPointerException e){
            return true;
        }
        return true;
    }

}
