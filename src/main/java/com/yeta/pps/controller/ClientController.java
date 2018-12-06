package com.yeta.pps.controller;

import com.yeta.pps.po.ClientIntegralDetail;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.ClientMembershipNumber;
import com.yeta.pps.service.ClientService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.ClientIntegralDetailVo;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 客户相关接口
 * @author YETA
 * @date 2018/12/04/14:19
 */
@Api(value = "客户相关接口")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 新增会员卡号接口
     * @param clientMembershipNumber
     * @return
     */
    @ApiOperation(value = "新增会员卡号")
    @ApiImplicitParam(name = "clientMembershipNumber", value = "number必填", required = true, paramType = "body", dataType = "ClientMembershipNumber")
    @PostMapping(value = "/clients/membershipNumbers")
    public CommonResponse addClientMembershipNumber(@RequestBody @Valid ClientMembershipNumber clientMembershipNumber) {
        return clientService.addClientMembershipNumber(clientMembershipNumber);
    }

    /**
     * 删除会员卡号接口
     * @param membershipNumberId
     * @return
     */
    @ApiOperation(value = "删除会员卡号")
    @ApiImplicitParam(name = "membershipNumberId", value = "会员卡号id", required = true, paramType = "path", dataType = "int")
    @DeleteMapping(value = "/clients/membershipNumbers/{membershipNumberId}")
    public CommonResponse deleteClientMembershipNumber(@PathVariable(value = "membershipNumberId") Integer membershipNumberId) {
        return clientService.deleteClientMembershipNumber(new ClientMembershipNumber(membershipNumberId));
    }

    /**
     * 修改会员卡号接口
     * @param clientMembershipNumber
     * @return
     */
    @ApiOperation(value = "修改会员卡号", notes = "只有未被使用的才可以修改")
    @ApiImplicitParam(name = "clientMembershipNumber", value = "id, number必填", required = true, paramType = "body", dataType = "ClientMembershipNumber")
    @PutMapping(value = "/clients/membershipNumbers")
    public CommonResponse updateClientMembershipNumber(@RequestBody @Valid ClientMembershipNumber clientMembershipNumber) {
        return clientService.updateClientMembershipNumber(clientMembershipNumber);
    }

    /**
     * 查找所有会员卡号接口
     * @param membershipNumber
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有会员卡号", notes = "分页、筛选查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/membershipNumbers")
    public CommonResponse<CommonResult<List<ClientMembershipNumber>>> findAllClientMembershipNumber(@RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                                                    @RequestParam(value = "page") Integer page,
                                                                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllClientMembershipNumber(new ClientMembershipNumber(membershipNumber), new PageVo(page, pageSize));
    }

    /**
     * 根据id查找会员卡号接口
     * @param membershipNumberId
     * @return
     */
    @ApiOperation(value = "根据id查找会员卡号")
    @ApiImplicitParam(name = "membershipNumberId", value = "会员卡号id", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/clients/membershipNumbers/{membershipNumberId}")
    public CommonResponse<ClientMembershipNumber> findClientMembershipNumberById(@PathVariable(value = "membershipNumberId") Integer membershipNumberId) {
        return clientService.findClientMembershipNumberById(new ClientMembershipNumber(membershipNumberId));
    }

    //

    /**
     * 新增客户级别接口
     * @param clientLevel
     * @return
     */
    @ApiOperation(value = "新增客户级别")
    @ApiImplicitParam(name = "clientLevel", value = "name必填", required = true, paramType = "body", dataType = "ClientLevel")
    @PostMapping(value = "/clients/levels")
    public CommonResponse addClientLevel(@RequestBody @Valid ClientLevel clientLevel) {
        return clientService.addClientLevel(clientLevel);
    }

    /**
     * 删除客户级别接口
     * @param levelId
     * @return
     */
    @ApiOperation(value = "删除客户级别")
    @ApiImplicitParam(name = "levelId", value = "客户级别id", required = true, paramType = "path", dataType = "int")
    @DeleteMapping(value = "/clients/levels/{levelId}")
    public CommonResponse deleteClientLevel(@PathVariable(value = "levelId") Integer levelId) {
        return clientService.deleteClientLevel(new ClientLevel(levelId));
    }

    /**
     * 修改客户级别接口
     * @param clientLevel
     * @return
     */
    @ApiOperation(value = "修改客户级别", notes = "只有未被使用的才可以修改")
    @ApiImplicitParam(name = "clientLevel", value = "id, name必填", required = true, paramType = "body", dataType = "ClientLevel")
    @PutMapping(value = "/clients/levels")
    public CommonResponse updateClientLevel(@RequestBody @Valid ClientLevel clientLevel) {
        return clientService.updateClientLevel(clientLevel);
    }

    /**
     * 查找所有客户级别接口
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有客户级别", notes = "分页查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/levels")
    public CommonResponse<CommonResult<List<ClientLevel>>> findAllClientLevel(@RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return clientService.findAllClientLevel(new PageVo(page, pageSize));
    }

    /**
     * 根据id查找客户级别接口
     * @param levelId
     * @return
     */
    @ApiOperation(value = "根据id查找客户级别")
    @ApiImplicitParam(name = "levelId", value = "客户级别id", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/clients/levels/{levelId}")
    public CommonResponse<ClientLevel> findClientLevelById(@PathVariable(value = "levelId") Integer levelId) {
        return clientService.findClientLevelById(new ClientLevel(levelId));
    }

    //

    /**
     * 新增客户接口
     * @param clientVo
     * @return
     */
    @ApiOperation(value = "新增客户")
    @ApiImplicitParam(name = "clientVo", value = "name, username, password, phone, levelId必填；createTime, disabled不填；其他选填", required = true, paramType = "body", dataType = "ClientVo")
    @PostMapping(value = "/clients")
    public CommonResponse add(@RequestBody ClientVo clientVo) {
        return clientService.add(clientVo);
    }

    /**
     * 删除客户接口
     * @param clientId
     * @return
     */
    @ApiOperation(value = "删除客户")
    @ApiImplicitParam(name = "clientId", value = "客户编号", required = true, paramType = "path", dataType = "int")
    @DeleteMapping(value = "/clients/{clientId}")
    public CommonResponse delete(@PathVariable(value = "clientId") String clientId) {
        return clientService.delete(new ClientVo(clientId));
    }

    /**
     * 修改客户接口
     * @param clientVo
     * @return
     */
    @ApiOperation(value = "修改客户")
    @ApiImplicitParam(name = "clientVo", value = "id, name, username, password, phone, levelId, disabled必填；createTime, disabled不填；其他选填", required = true, paramType = "body", dataType = "ClientVo")
    @PutMapping(value = "/clients")
    public CommonResponse update(@RequestBody ClientVo clientVo) {
        return clientService.update(clientVo);
    }

    /**
     * 修改客户积分接口
     * @param clientIntegralDetailVo
     * @return
     */
    @ApiOperation(value = "修改客户积分")
    @ApiImplicitParam(name = "clientIntegralDetailVo", value = "userId, clientId, type, changeIntegral必填；remark选填", required = true, paramType = "body", dataType = "ClientIntegralDetailVo")
    @PutMapping(value = "/clients/integrals")
    public CommonResponse updateIntegral(@RequestBody ClientIntegralDetailVo clientIntegralDetailVo) {
        return clientService.updateIntegral(clientIntegralDetailVo);
    }

    /**
     * 查找所有客户接口
     *
     * @param id
     * @param name
     * @param phone
     * @param levelId
     * @param membershipNumber
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有客户", notes = "分页、筛选查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "客户姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "levelId", value = "客户级别id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients")
    public CommonResponse<CommonResult<List<ClientVo>>> findAll(@RequestParam(value = "id", required = false) String id,
                                                                @RequestParam(value = "name", required = false) String name,
                                                                @RequestParam(value = "phone", required = false) String phone,
                                                                @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                @RequestParam(value = "levelId", required = false) Integer levelId,
                                                                @RequestParam(value = "page") Integer page,
                                                                @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAll(new ClientVo(id, name, phone, membershipNumber, levelId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查找客户接口
     * @param clientId
     * @return
     */
    @ApiOperation(value = "根据id查找客户")
    @ApiImplicitParam(name = "clientId", value = "客户id", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/clients/{clientId}")
    public CommonResponse<ClientVo> findById(@PathVariable(value = "clientId") String clientId) {
        return clientService.findById(new ClientVo(clientId));
    }

    /**
     * 导出客户接口
     * @param id
     * @param name
     * @param phone
     * @param membershipNumber
     * @param levelId
     */
    @ApiOperation(value = "导出客户", notes = "可筛选导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "levelId", value = "客户级别id", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/clients/export")
    public void exportClient(@RequestParam(value = "id", required = false) String id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                             @RequestParam(value = "levelId", required = false) Integer levelId,
                             HttpServletResponse response) throws IOException {
        clientService.exportClient(new ClientVo(id, name, phone, membershipNumber, levelId), response);
    }

    /**
     * 获取导入模版接口
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "获取导入客户模版")
    @GetMapping(value = "/clients/import/template")
    public void getImportClientTemplate(HttpServletResponse response) throws IOException {
        clientService.getImportClientTemplate(response);
    }

    /**
     * 导入客户接口
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "导入客户")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataType = "File")
    @PostMapping(value = "/clients/import")
    public CommonResponse importClient(@RequestParam(value = "file") MultipartFile file) throws IOException, ParseException {
        return clientService.importClient(file);
    }

    //

    /**
     * 查找所有客户积分明细接口
     * @param type
     * @param invoicesId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有客户积分", notes = "分页、筛选查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "操作类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "invoicesId", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/integrals/details")
    public CommonResponse<CommonResult<List<ClientIntegralDetail>>> findAllIntegralDetail(@RequestParam(value = "type", required = false) Byte type,
                                                                                          @RequestParam(value = "invoicesId", required = false) String invoicesId,
                                                                                          @RequestParam(value = "page") Integer page,
                                                                                          @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllIntegralDetail(new ClientIntegralDetail(type, invoicesId), new PageVo(page, pageSize));
    }
}
