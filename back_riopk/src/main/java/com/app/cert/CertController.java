package com.app.cert;

import com.app.cert.converter.CertDtoToCertConverter;
import com.app.cert.converter.CertToCertDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.MANAGER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certs")
public class CertController {

    private final CertService service;
    private final CertDtoToCertConverter toConverter;
    private final CertToCertDtoConverter toDtoConverter;

    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({MANAGER})
    @PostMapping
    public Result save(@RequestBody CertDto saveDto, @RequestParam String reason, @RequestParam String categoryId) {
        Cert save = toConverter.convert(saveDto);
        Cert saved = service.save(save, reason, categoryId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({MANAGER})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody CertDto updateDto, @RequestParam String reason, @RequestParam String categoryId) {
        Cert update = toConverter.convert(updateDto);
        Cert updated = service.update(id, update, reason, categoryId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/img")
    public Result updateImg(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(id, files))
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/file")
    public Result updateFile(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update File",
                toDtoConverter.convert(service.updateFile(id, files))
        );
    }

    @Secured({MANAGER})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete"
        );
    }

}
