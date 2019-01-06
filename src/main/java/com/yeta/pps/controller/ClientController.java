package com.yeta.pps.controller;

import com.yeta.pps.po.Client;
import com.yeta.pps.po.ClientIntegralDetail;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.MembershipNumber;
import com.yeta.pps.service.ClientService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.ClientIntegralDetailVo;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreIntegralVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

    //客户登陆注销

    /**
     * 客户登陆接口
     * @param username
     * @param password
     * @param identifyingCode
     * @param request
     * @return
     */
    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "identifyingCode", value = "验证码", required = true, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/clients/login")
    public CommonResponse login(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "identifyingCode") String identifyingCode,
                                HttpServletRequest request) {
        return clientService.login(new ClientVo(username, password, identifyingCode), request);
    }

    /**
     * 客户注销接口
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "注销")
    @ApiImplicitParam(name = "id", value = "客户编号", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/clients/logout/{id}")
    public CommonResponse logout(@PathVariable(value = "id") String id,
                                 HttpServletRequest request) {
        return clientService.logout(new ClientVo(id), request);
    }


    //会员卡号

    /**
     * 新增会员卡号接口
     * @param membershipNumber
     * @return
     */
    @ApiOperation(value = "新增会员卡号", notes = "特权账号使用")
    @ApiImplicitParam(name = "membershipNumber", value = "number必填", required = true, paramType = "body", dataType = "MembershipNumber")
    @PostMapping(value = "/clients/membershipNumbers")
    public CommonResponse addMembershipNumber(@RequestBody @Valid MembershipNumber membershipNumber) {
        return clientService.addMembershipNumber(membershipNumber);
    }

    /**
     * 删除会员卡号接口
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除会员卡号", notes = "特权账号使用")
    @ApiImplicitParam(name = "ids", value = "会员卡号id", required = true, paramType = "query", dataType = "String")
    @DeleteMapping(value = "/clients/membershipNumbers")
    public CommonResponse deleteMembershipNumber(@RequestParam(value = "ids") String ids) {
        List<MembershipNumber> membershipNumbers = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            membershipNumbers.add(new MembershipNumber(Integer.valueOf(id)));
        });
        return clientService.deleteMembershipNumber(membershipNumbers);
    }

    /**
     * 修改会员卡号接口
     * @param membershipNumber
     * @return
     */
    @ApiOperation(value = "修改会员卡号", notes = "特权账号使用")
    @ApiImplicitParam(name = "membershipNumber", value = "id, number, disabled必填", required = true, paramType = "body", dataType = "MembershipNumber")
    @PutMapping(value = "/clients/membershipNumbers")
    public CommonResponse updateMembershipNumber(@RequestBody @Valid MembershipNumber membershipNumber) {
        return clientService.updateMembershipNumber(membershipNumber);
    }

    /**
     * 查找所有会员卡号接口
     * @param number
     * @param disabled
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有会员卡号", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "disabled", value = "是否停用，0：否，1：是", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/membershipNumbers")
    public CommonResponse<CommonResult<List<MembershipNumber>>> findAllMembershipNumber(@RequestParam(value = "number", required = false) String number,
                                                                                        @RequestParam(value = "disabled", required = false) Byte disabled,
                                                                                        @RequestParam(value = "page") Integer page,
                                                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllMembershipNumber(new MembershipNumber(number, disabled), new PageVo(page, pageSize));
    }

    //客户级别

    /**
     * 新增客户级别接口
     * @param clientLevel
     * @return
     */
    @ApiOperation(value = "新增客户级别", notes = "特权账号使用")
    @ApiImplicitParam(name = "clientLevel", value = "name, priceType(级别价格类型，1：零售价，2：vip售价), price(级别默认价格，级别价格类型*0.几)必填", required = true, paramType = "body", dataType = "ClientLevel")
    @PostMapping(value = "/clients/levels")
    public CommonResponse addClientLevel(@RequestBody @Valid ClientLevel clientLevel) {
        return clientService.addClientLevel(clientLevel);
    }

    /**
     * 删除客户级别接口
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除客户级别", notes = "特权账号使用")
    @ApiImplicitParam(name = "ids", value = "客户级别id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    @DeleteMapping(value = "/clients/levels")
    public CommonResponse deleteClientLevel(@RequestParam(value = "ids") String ids) {
        List<ClientLevel> clientLevels = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            clientLevels.add(new ClientLevel(Integer.valueOf(id)));
        });
        return clientService.deleteClientLevel(clientLevels);
    }

    /**
     * 修改客户级别接口
     * @param clientLevel
     * @return
     */
    @ApiOperation(value = "修改客户级别", notes = "特权账号使用")
    @ApiImplicitParam(name = "clientLevel", value = "id, name, priceType(级别价格类型，1：零售价，2：vip售价), price(级别默认价格，级别价格类型*0.几)必填", required = true, paramType = "body", dataType = "ClientLevel")
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
    @ApiOperation(value = "查找所有客户级别", notes = "总店/分店都可用，分页用于表格，不分页用于新增客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/clients/levels")
    public CommonResponse<CommonResult<List<ClientLevel>>> findAllClientLevel(@RequestParam(value = "page", required = false) Integer page,
                                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return clientService.findAllClientLevel(new PageVo(page, pageSize));
    }

    //客户

    /**
     * 新增客户接口
     * @param clientVo
     * @return
     */
    @ApiOperation(value = "新增客户", notes = "用户名后台默认电话号码，密码后台默认电话号码后4位")
    @ApiImplicitParam(name = "clientVo", value = "name, phone, levelId, membershipNumber必填, inviterId, inviterPhone(二选一填), birthday, address, postcode选填, 其他不填", required = true, paramType = "body", dataType = "ClientVo")
    @PostMapping(value = "/clients")
    public CommonResponse add(@RequestBody ClientVo clientVo) {
        return clientService.add(clientVo);
    }

    /**
     * 删除客户接口
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除客户")
    @ApiImplicitParam(name = "ids", value = "客户编号", required = true, paramType = "query", dataType = "String")
    @DeleteMapping(value = "/clients")
    public CommonResponse delete(@RequestParam(value = "ids") String ids) {
        List<ClientVo> clientVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            clientVos.add(new ClientVo(id));
        });
        return clientService.delete(clientVos);
    }

    /**
     * 客户自己修改信息接口
     * @param client
     * @return
     */
    @ApiOperation(value = "客户自己修改信息")
    @ApiImplicitParam(name = "client", value = "id, name, password, phone, 必填, birthday, address, postcode选填, 其他不填", required = true, paramType = "body", dataType = "ClientVo")
    @PutMapping(value = "/clients")
    public CommonResponse update(@RequestBody @Valid Client client) {
        ClientVo clientVo = new ClientVo();
        clientVo.setId(client.getId());
        clientVo.setName(client.getName());
        clientVo.setPassword(client.getPassword());
        clientVo.setPhone(client.getPhone());
        clientVo.setBirthday(client.getBirthday());
        clientVo.setAddress(client.getAddress());
        clientVo.setPostcode(client.getPostcode());
        return clientService.update(clientVo);
    }

    /**
     * 修改客户停用和备注接口
     * @param id
     * @param disabled
     * @param remark
     * @return
     */
    @ApiOperation(value = "修改客户停用和备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "disabled", value = "是否停用，0：否，1：是", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String"),
    })
    @PutMapping(value = "/clients/disabled")
    public CommonResponse updateDisabledAndRemark(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "disabled") Byte disabled,
                                                  @RequestParam(value = "remark", required = false) String remark) {
        return clientService.updateDisabledAndRemark(new ClientVo(id, disabled, remark));
    }

    /**
     * 查找所有客户接口
     * @param id
     * @param name
     * @param phone
     * @param levelId
     * @param membershipNumber
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查找所有客户", notes = "筛选查找，除了客户级别都是模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "客户姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "levelId", value = "客户级别编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "disabled", value = "是否禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients")
    public CommonResponse<CommonResult<List<ClientVo>>> findAll(@RequestParam(value = "id", required = false) String id,
                                                                @RequestParam(value = "name", required = false) String name,
                                                                @RequestParam(value = "phone", required = false) String phone,
                                                                @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                @RequestParam(value = "levelId", required = false) Integer levelId,
                                                                @RequestParam(value = "disabled", required = false) Byte disabled,
                                                                @RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return clientService.findAll(new ClientVo(id, name, phone, levelId, membershipNumber, disabled), new PageVo(page, pageSize));
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
            @ApiImplicitParam(name = "levelId", value = "客户级别id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "disabled", value = "是否禁用", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/clients/export")
    public void exportClient(@RequestParam(value = "id", required = false) String id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                             @RequestParam(value = "levelId", required = false) Integer levelId,
                             @RequestParam(value = "disabled") Byte disabled,
                             HttpServletResponse response) throws IOException {
        clientService.exportClient(new ClientVo(id, name, phone, levelId, membershipNumber, disabled), response);
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
     * @throws ParseException
     */
    @ApiOperation(value = "导入客户")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataType = "File")
    @PostMapping(value = "/clients/import")
    public CommonResponse importClient(@RequestParam(value = "file") MultipartFile file) throws IOException, ParseException {
        return clientService.importClient(file);
    }

    //店铺/积分关系

    /**
     * 查询客户积分接口
     * @param clientId
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询客户积分", notes = "默认分页，客户查询自己的积分情况传clientId，店铺查询自己店铺的客户的积分情况传storeId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "storeId", value = "客户姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/integral")
    public CommonResponse<CommonResult<List<StoreIntegralVo>>> findAllStoreIntegral(@RequestParam(value = "clientId", required = false) String clientId,
                                                                                    @RequestParam(value = "storeId", required = false) Integer storeId,
                                                                                    @RequestParam(value = "page") Integer page,
                                                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllStoreIntegral(new StoreIntegralVo(storeId, clientId), new PageVo(page, pageSize));
    }

    //客户/积分明细

    /**
     * 查询客户积分明细接口
     * @param clientId
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询客户积分", notes = "默认分页，客户查询自己的积分情况传clientId，店铺查询自己店铺的客户的积分情况传storeId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "storeId", value = "客户姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "操作类型，0：减少，1：增加", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/integral/detail")
    public CommonResponse<CommonResult<List<ClientIntegralDetailVo>>> findAllIntegralDetail(@RequestParam(value = "clientId", required = false) String clientId,
                                                                                            @RequestParam(value = "storeId", required = false) Integer storeId,
                                                                                            @RequestParam(value = "type", required = false) Byte type,
                                                                                            @RequestParam(value = "page") Integer page,
                                                                                            @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllIntegralDetail(new ClientIntegralDetailVo(storeId, clientId, type), new PageVo(page, pageSize));
    }
}
