package com.app.stats;

import com.app.category.Category;
import com.app.category.CategoryService;
import com.app.cert.Cert;
import com.app.cert.CertService;
import com.app.enums.OrderingStatus;
import com.app.ordering.OrderingService;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.app.util.Global.ADMIN;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Secured({ADMIN})
public class StatsController {

    private final CertService certService;
    private final CategoryService categoryService;
    private final OrderingService orderingService;

    @GetMapping("/orderings/size")
    public Result orderingsSize() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        List<Cert> certs = certService.findAll();

        certs.sort(Comparator.comparing(Cert::getOrderingsSize).reversed());

        for (int i = 0; i < certs.size(); i++) {
            if (i == 5) break;

            names.add(certs.get(i).getName());
            values.add(certs.get(i).getOrderingsSize());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Orderings Size",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/categories/certs/size")
    public Result categoriesCertsSize() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        List<Category> categories = categoryService.findAll();

        categories.sort(Comparator.comparing(Category::getCertsSize).reversed());

        for (Category category : categories) {
            names.add(category.getName());
            values.add(category.getCertsSize());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Categories Certs Size",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/orderings/status")
    public Result orderingsStatus() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (OrderingStatus status : OrderingStatus.values()) {
            names.add(status.getName());
            values.add(orderingService.findAllByStatus(status).size());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Orderings Status",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/certs/ctr")
    public Result certsCtr() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        List<Cert> certs = certService.findAll();

        certs.sort(Comparator.comparing(Cert::getCtr).reversed());

        for (int i = 0; i < certs.size(); i++) {
            if (i == 5) break;

            names.add(certs.get(i).getName());
            values.add(certs.get(i).getCtr());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Certs Ctr",
                Collections.unmodifiableMap(res)
        );
    }

}
