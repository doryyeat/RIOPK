package com.app.cert;

import com.app.app_user.UserService;
import com.app.category.CategoryService;
import com.app.enums.CertReason;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class CertService {

    private final CertRepository repository;
    private final CategoryService categoryService;
    private final UserService userService;

    public List<Cert> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Cert find(String id) {
        Cert cert = repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найден сертификат по ИД: " + id));
        cert.setViews(cert.getViews() + 1);
        return repository.save(cert);
    }

    public Cert findForTest(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найден сертификат по ИД: " + id));
    }

    public Cert save(Cert save, String reason, String categoryId) {
        setReason(save, reason);
        save.setCategory(categoryService.find(categoryId));
        save.setOwner(userService.getCurrentUser());
        return repository.save(save);
    }

    public Cert saveForTest(Cert save) {
        return repository.save(save);
    }

    public Cert update(String id, Cert update, String reason, String categoryId) {
        Cert old = find(id);
        old.update(update);
        setReason(old, reason);
        old.setCategory(categoryService.find(categoryId));
        return repository.save(old);
    }

    public Cert updateForTest(String id, Cert update) {
        Cert old = findForTest(id);
        old.update(update);
        return repository.save(old);
    }

    public Cert updateImg(String id, MultipartFile img) {
        Cert cert = find(id);
        try {
            cert.setImg(saveFile(img, "cert"));
        } catch (IOException e) {
            if (cert.getImg().isEmpty()) repository.deleteById(cert.getId());
            throw new BadRequestException("Некорректное изображение");
        }
        return repository.save(cert);
    }

    public Cert updateFile(String id, MultipartFile file) {
        Cert cert = find(id);
        try {
            cert.setFile(saveFile(file, "cert"));
        } catch (IOException e) {
            if (cert.getFile().isEmpty()) repository.deleteById(cert.getId());
            throw new BadRequestException("Некорректный файл");
        }
        return repository.save(cert);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }

    public void deleteForTest(String id) {
        repository.deleteById(findForTest(id).getId());
    }

    private void setReason(Cert cert, String reason) {
        try {
            cert.setReason(CertReason.valueOf(reason));
        } catch (Exception e) {
            throw new BadRequestException("Некорректный выбор повода");
        }
    }

}
