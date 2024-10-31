package com.example.pet_care.service.photo;

import com.example.pet_care.entities.Photo;
import com.example.pet_care.entities.User;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.repository.PhotoRepository;
import com.example.pet_care.repository.UserRepository;
import com.example.pet_care.utils.FeedBackMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoManager implements PhotoService {
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    @Override
    public Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException {
        Optional<User> theUser=userRepository.findById(userId);
        Photo photo=new Photo();
        if(file!=null && !file.isEmpty()){
            byte[] photoBytes=file.getBytes();//dosyanın byte dizisini döndürür.
            Blob photoBlob=new SerialBlob(photoBytes);//photoBytes dizisini kullanarak bir Blob nesnesi oluşturur. db ye yazmak için
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
        }
        Photo savedPhoto=photoRepository.save(photo);
        theUser.ifPresent(user -> {user.setPhoto(savedPhoto);});
        userRepository.save(theUser.get());
        return savedPhoto;
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_FOUND));
    }
    @Transactional
    @Override
    public void deletePhoto(Long id, Long userId) {
        userRepository.findById(userId).ifPresentOrElse(User::removeUserPhoto,()->{
            throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
        });
        photoRepository.findById(id).ifPresentOrElse(photoRepository::delete,()->{
            throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
        });
    }

    @Override
    public Photo updatePhoto(Long id, MultipartFile file) throws SQLException, IOException {
        Photo photo = getPhotoById(id);

        if (photo != null) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
            return photoRepository.save(photo);
        }

        throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_FOUND);
    }

    @Override
    public byte[] getImageData(Long id) throws SQLException {
        Photo photo = getPhotoById(id);
        if (photo != null) {
            Blob photoBlob = photo.getImage();
            int blobLength = (int) photoBlob.length();
            return photoBlob.getBytes(1, blobLength);
        }
        return null;
    }
}
