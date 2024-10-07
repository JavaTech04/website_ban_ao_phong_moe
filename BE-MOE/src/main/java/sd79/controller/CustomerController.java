package sd79.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sd79.dto.requests.CustomerReq;
import sd79.dto.response.CustomerResponse;
import sd79.dto.response.ResponseData;
import sd79.model.Coupon;
import sd79.model.Customer;
import sd79.service.CustomerService;
import sd79.service.impl.CustomerServiceImpl;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/${api.version}/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseData<?> getAll() {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", customerService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable long id){
        customerService.delete(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Success");
    }
    @PostMapping("/store")
    public ResponseData<?> add(@Valid @RequestBody CustomerReq customerReq){
        customerService.save(customerReq);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Success");
    }
    @PutMapping("/update/{id}")
    public ResponseData<?> update(@PathVariable Long id, @Valid @RequestBody CustomerReq customerReq){
        customerService.update(id, customerReq);
        return new ResponseData<>(HttpStatus.OK.value(), "Success");
    }
    @GetMapping("/searchName")
    public ResponseData<?> searchKeywordAndDate(
            @RequestParam(value = "fistName", required = false) String fistName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber){

        List<Customer> results = customerService.findByNameOrPhone(fistName, lastName, phoneNumber);
        return new ResponseData<>(HttpStatus.OK.value(), "Search results", results);
    }
}
