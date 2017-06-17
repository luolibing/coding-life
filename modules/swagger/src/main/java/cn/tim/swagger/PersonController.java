package cn.tim.swagger;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User: luolibing
 * Date: 2017/6/2 15:43
 */
@Api(value = "/api/person", tags = "Persons", description = "person测试类")
@RequestMapping(value = "/api/person")
@RestController
public class PersonController {


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取person", notes = "id不能为空", response = Person.class, authorizations = {
            @Authorization(value = "apiKey"),
            @Authorization(value = "petstore_auth", scopes = {
                    @AuthorizationScope(scope = "readPerson", description = "需要读权限")
            })
    })
    public Person find(
            @ApiParam(value = "用户id",
                    // allowableValues = "rang[0-3276843]",
                    required = true)
            @PathVariable Long id) {
        return new Person();
    }


    @PostMapping
    @ApiOperation(value = "保存person", notes = "保存person", response = Person.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "client错误"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    public ResponseEntity<Person> save(
            @ApiParam(value = "表单参数", required = true)
            @RequestBody Person person) {
        return ResponseEntity.ok(person);
    }
}
