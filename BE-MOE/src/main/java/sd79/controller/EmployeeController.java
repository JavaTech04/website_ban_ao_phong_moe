package sd79.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sd79.dto.requests.EmployeeReq;
import sd79.dto.requests.EmployeeRequest;
import sd79.dto.requests.employees.EmployeeImageReq;
import sd79.dto.requests.employees.PasswordUpdateRequest;
import sd79.dto.response.EmployeeResponse;
import sd79.dto.response.ResponseData;
import sd79.exception.EntityNotFoundException;
import sd79.repositories.PositionsRepository;
import sd79.service.EmployeeService;

import java.util.List;


@RestController
@RequestMapping("api/${api.version}/employee")
@Tag(name = "Employee Controller", description = "Manage adding, editing, and deleting product employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final PositionsRepository positionsRepository;

    @Operation(
            summary = "Get Employee",
            description = "Get all employee from database"
    )
    @GetMapping
    public ResponseData<?> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeResponse> employeePage = employeeService.getEmployee(pageable);
        return new ResponseData<>(HttpStatus.OK.value(), "List employee", employeePage);
    }


    @GetMapping("/positions")
    public ResponseData<?> getPositions() {
        return new ResponseData<>(HttpStatus.OK.value(), "List positions", positionsRepository.findAll());
    }


    @Operation(
            summary = "Get Employee by id",
            description = "Get employee by from database"
    )
    @GetMapping("{id}")
    public ResponseData<?> getEmployeeById(@PathVariable Long id) {
        return new ResponseData<>(HttpStatus.OK.value(), "Employee details", employeeService.getEmployeeById(id));
    }

    @Operation(
            summary = "New Employee",
            description = "New employee into database"
    )
    @PostMapping
    public ResponseData<?> addEmployee(@Valid @RequestBody EmployeeReq req) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Thêm thành công", employeeService.storeEmployee(req));
    }

    // Cập nhật employee
    @Operation(
            summary = "Update Employee",
            description = "Update employee into database"
    )
    @PutMapping("/{id}")
    public ResponseData<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeReq employeeRequest) {
        employeeService.updateEmp(employeeRequest, id);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Sửa thành công");
    }

    // Xóa employee
    @Operation(
            summary = "Delete Employee",
            description = "Set is delete of employee to true and hidde from from"
    )
    @DeleteMapping("{id}")
    public ResponseData<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Xóa nhân viên thành công!");
    }

    @GetMapping("/searchNameAndPhone")
    public ResponseData<?> searchNameAndPhone(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "phone_number", required = false) String phone_number) {

        List<EmployeeResponse> results = employeeService.findByNameAndPhone(keyword, phone_number);
        return new ResponseData<>(HttpStatus.OK.value(), "Search results", results);
    }

    @Operation(
            summary = "Upload image",
            description = "Upload the image to cloudinary and return the url to save to the database"
    )
    @PostMapping("/upload")
    public ResponseData<?> uploadFile(@Valid @ModelAttribute EmployeeImageReq request) {
        this.employeeService.updateImage(request);
        return new ResponseData<>(HttpStatus.CREATED.value(), "");
    }

    @PatchMapping("/change-isLocked/{id}/{isLocked}")
    public ResponseData<?> setUserLocked(@Min(1) @PathVariable("id") long id, @PathVariable("isLocked") Boolean isLocked) {
        this.employeeService.setUserLocked(id, isLocked);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Thay đổi trạng thái thành công");
    }

    @PutMapping("/{id}/update-password")
    public ResponseData<String> updatePassword(@PathVariable long id, @RequestBody PasswordUpdateRequest request) {
        try {
            employeeService.updatePassword(request, id);
            return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật mật khẩu thành công");
        } catch (EntityNotFoundException e) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Không tìm thấy người dùng với ID: " + id);
        } catch (IllegalArgumentException e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage()); // Trả về thông điệp lỗi chi tiết
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra, vui lòng thử lại sau");
        }
    }

    @GetMapping("/me/{id}")
    public ResponseData<?> getEmployeeDetailByUserId(@PathVariable long id) {
        return new ResponseData<>(HttpStatus.OK.value(), "Employee details", employeeService.detailByUserId(id));

    }

    @PutMapping("/update/{id}")
    public ResponseData<?> updateEmployeeByUserId(
            @PathVariable long id,
            @Valid @RequestBody EmployeeReq request) {
        employeeService.updateByUserId(id, request);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Sửa thành công");
    }


}
