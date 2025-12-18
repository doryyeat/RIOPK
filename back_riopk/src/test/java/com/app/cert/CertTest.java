package com.app.cert;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertTest {

    @Mock
    CertRepository repository;
    @InjectMocks
    CertService service;

    List<Cert> certs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        certs.add(new Cert(1L, "name1", "address1", 1.99f, 1, "description1"));
        certs.add(new Cert(2L, "name2", "address2", 2.99f, 2, "description2"));
        certs.add(new Cert(3L, "name3", "address3", 3.99f, 3, "description3"));
        certs.add(new Cert(4L, "name4", "address4", 4.99f, 4, "description4"));
    }

    @AfterEach
    void tearDown() {
        certs.clear();
    }

    @Test
    void testFindAllSuccess() {
        given(service.findAll()).willReturn(certs);

        List<Cert> findAll = service.findAll();

        assertThat(findAll.size()).isEqualTo(certs.size());

        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void testFindSuccess() {
        Cert cert = certs.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(cert));

        Cert find = service.findForTest("1");

        assertThat(find.getId()).isEqualTo(cert.getId());
        assertThat(find.getName()).isEqualTo(cert.getName());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindNotFound() {
        given(repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.find("1"));

        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testSaveSuccess() {
        Cert save = certs.get(0);

        given(repository.save(save)).willReturn(save);

        Cert saved = service.saveForTest(save);

        assertThat(saved.getName()).isEqualTo(save.getName());
        assertThat(saved.getAddress()).isEqualTo(save.getAddress());
        assertThat(saved.getPrice()).isEqualTo(save.getPrice());
        assertThat(saved.getTerm()).isEqualTo(save.getTerm());
        assertThat(saved.getDescription()).isEqualTo(save.getDescription());

        verify(repository, times(1)).save(save);
    }

    @Test
    void testUpdateSuccess() {
        Cert old = certs.get(0);

        Cert update = new Cert("update name", "update address", 9.99f, 9, "update description");

        given(repository.findById(1L)).willReturn(Optional.of(old));
        given(repository.save(old)).willReturn(old);

        Cert updated = service.updateForTest(old.getId() + "", update);

        assertThat(updated.getName()).isEqualTo(update.getName());
        assertThat(updated.getAddress()).isEqualTo(update.getAddress());
        assertThat(updated.getPrice()).isEqualTo(update.getPrice());
        assertThat(updated.getTerm()).isEqualTo(update.getTerm());
        assertThat(updated.getDescription()).isEqualTo(update.getDescription());

        verify(repository, times(1)).findById(old.getId());
        verify(repository, times(1)).save(old);
    }

    @Test
    void testUpdateNotFound() {
        Cert update = certs.get(0);

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest("1", update));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDeleteSuccess() {
        Cert delete = certs.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(delete));
        doNothing().when(repository).deleteById(1L);

        service.deleteForTest("1");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.delete("1"));

        verify(repository, times(1)).findById(1L);
    }

}
