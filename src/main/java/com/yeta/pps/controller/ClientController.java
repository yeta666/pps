package com.yeta.pps.controller;

import com.yeta.pps.po.Client;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.MembershipNumber;
import com.yeta.pps.po.StoreClientDetail;
import com.yeta.pps.service.ClientService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.*;
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
     * @return
     */
    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/clients/login")
    public CommonResponse login(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password) {
        ClientVo clientVo = new ClientVo();
        clientVo.setUsername(username);
        clientVo.setPhone(password);
        return clientService.login(clientVo);
    }

    //会员卡号

    /**
     * 新增会员卡号接口
     * @param membershipNumber
     * @param check
     * @return
     */
    @ApiOperation(value = "新增会员卡号", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "membershipNumber", value = "number必填", required = true, paramType = "body", dataType = "MembershipNumber"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping(value = "/clients/membershipNumbers/{check}")
    public CommonResponse addMembershipNumber(@RequestBody @Valid MembershipNumber membershipNumber,
                                              @PathVariable(value = "check") String check) {
        return clientService.addMembershipNumber(membershipNumber, check);
    }

    /**
     * 删除会员卡号接口
     * @param ids
     * @param check
     * @return
     */
    @ApiOperation(value = "删除会员卡号", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "会员卡号id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @DeleteMapping(value = "/clients/membershipNumbers/{check}")
    public CommonResponse deleteMembershipNumber(@RequestParam(value = "ids") String ids,
                                                 @PathVariable(value = "check") String check) {
        List<MembershipNumber> membershipNumbers = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            membershipNumbers.add(new MembershipNumber(Integer.valueOf(id)));
        });
        return clientService.deleteMembershipNumber(membershipNumbers, check);
    }

    /**
     * 修改会员卡号接口
     * @param membershipNumber
     * @param check
     * @return
     */
    @ApiOperation(value = "修改会员卡号", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "membershipNumber", value = "id, number, disabled必填", required = true, paramType = "body", dataType = "MembershipNumber"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/clients/membershipNumbers/{check}")
    public CommonResponse updateMembershipNumber(@RequestBody @Valid MembershipNumber membershipNumber,
                                                 @PathVariable(value = "check") String check) {
        return clientService.updateMembershipNumber(membershipNumber, check);
    }

    /**
     * 查找所有会员卡号接口
     * @param number
     * @param disabled
     * @param page
     * @param pageSize
     * @param check
     * @return
     */
    @ApiOperation(value = "查找所有会员卡号", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "disabled", value = "是否停用，0：否，1：是", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/clients/membershipNumbers/{check}")
    public CommonResponse<CommonResult<List<MembershipNumber>>> findAllMembershipNumber(@RequestParam(value = "number", required = false) String number,
                                                                                        @RequestParam(value = "disabled", required = false) Byte disabled,
                                                                                        @RequestParam(value = "page") Integer page,
                                                                                        @RequestParam(value = "pageSize") Integer pageSize,
                                                                                        @PathVariable(value = "check") String check) {
        return clientService.findAllMembershipNumber(new MembershipNumber(number, disabled), new PageVo(page, pageSize), check);
    }

    //客户级别

    /**
     * 新增客户级别接口
     * @param clientLevel
     * @param check
     * @return
     */
    @ApiOperation(value = "新增客户级别", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientLevel", value = "name, priceType(级别价格类型，1：零售价，2：vip售价), price(级别默认价格，级别价格类型*0.几)必填", required = true, paramType = "body", dataType = "ClientLevel"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping(value = "/clients/levels/{check}")
    public CommonResponse addClientLevel(@RequestBody @Valid ClientLevel clientLevel,
                                         @PathVariable(value = "check") String check) {
        return clientService.addClientLevel(clientLevel, check);
    }

    /**
     * 删除客户级别接口
     * @param ids
     * @param check
     * @return
     */
    @ApiOperation(value = "删除客户级别", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "客户级别id，英文逗号隔开", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @DeleteMapping(value = "/clients/levels/{check}")
    public CommonResponse deleteClientLevel(@RequestParam(value = "ids") String ids,
                                            @PathVariable(value = "check") String check) {
        List<ClientLevel> clientLevels = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            clientLevels.add(new ClientLevel(Integer.valueOf(id)));
        });
        return clientService.deleteClientLevel(clientLevels, check);
    }

    /**
     * 修改客户级别接口
     * @param clientLevel
     * @param check
     * @return
     */
    @ApiOperation(value = "修改客户级别", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientLevel", value = "id, name, priceType(级别价格类型，1：零售价，2：vip售价), price(级别默认价格，级别价格类型*0.几)必填", required = true, paramType = "body", dataType = "ClientLevel"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/clients/levels/{check}")
    public CommonResponse updateClientLevel(@RequestBody @Valid ClientLevel clientLevel,
                                            @PathVariable(value = "check") String check) {
        return clientService.updateClientLevel(clientLevel, check);
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
     * @param check
     * @return
     */
    @ApiOperation(value = "删除客户", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "客户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @DeleteMapping(value = "/clients/{check}")
    public CommonResponse delete(@RequestParam(value = "ids") String ids,
                                 @PathVariable(value = "check") String check) {
        List<ClientVo> clientVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            clientVos.add(new ClientVo(id));
        });
        return clientService.delete(clientVos, check);
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
    @ApiOperation(value = "修改客户停用和备注", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "disabled", value = "是否停用，0：否，1：是", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/clients/disabled/{check}")
    public CommonResponse updateDisabledAndRemark(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "disabled") Byte disabled,
                                                  @RequestParam(value = "remark", required = false) String remark,
                                                  @PathVariable(value = "check") String check) {
        return clientService.updateDisabledAndRemark(new ClientVo(id, disabled, remark), check);
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
     * 客户查询自己的信息接口
     * @param id
     * @return
     */
    @ApiOperation(value = "客户查询自己的信息", notes = "返回了上级是谁")
    @ApiImplicitParam(name = "id", value = "客户编号", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/clients/{id}")
    public CommonResponse<ClientVo> findById(@PathVariable(value = "id") String id) {
        return clientService.findById(new ClientVo(id));
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
                             @RequestParam(value = "disabled", required = false) Byte disabled,
                             HttpServletResponse response) {
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

    //店铺/客户关系

    /**
     * 查询店铺/客户关系接口
     * @param storeId
     * @param clientId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询店铺/客户关系", notes = "客户查询在各个店铺的积分、预收款余额、提成只传clientId；店铺查询各个会员的积分、预收款余额、提成只传sotreId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/stores")
    public CommonResponse<CommonResult<List<StoreClientVo>>> findAllStoreClient(@RequestParam(value = "storeId", required = false) Integer storeId,
                                                                                @RequestParam(value = "clientId", required = false) String clientId,
                                                                                @RequestParam(value = "page") Integer page,
                                                                                @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllStoreClient(new StoreClientVo(storeId, clientId), new PageVo(page, pageSize));
    }

    /**
     * 积分或提成提现接口
     * @param storeClientDetail
     * @return
     */
    @ApiOperation(value = "积分或提成提现", notes = "积分提现传type = 2, changeIntegral，提成提现传type = 4, changePushMoney")
    @ApiImplicitParam(name = "storeClientDetail", value = "storeId, clientId, type, changeIntegral或changePushMoney, withdrawalWay必填，提现方式为2时必填remark", required = true, paramType = "body", dataType = "StoreClientDetail")
    @PostMapping(value = "/clients/withdraw")
    public CommonResponse clientWithdraw(@RequestBody @Valid StoreClientDetail storeClientDetail) {
        return clientService.clientWithdraw(storeClientDetail);
    }

    //店铺/客户明细关系

    /**
     * 查询店铺/客户明细关系接口
     * @param storeId
     * @param clientId
     * @param clientName
     * @param type
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询店铺/客户明细关系", notes = "客户查询在各个店铺的积分明细、提成明细只传clientId；店铺查询各个会员的积分明细、提成明细只传sotreId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "clientName", value = "客户姓名，模糊查询", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型，1：积分增加，2：积分减少，3：提成增加，4：提成减少", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "状态，0：待审核，1：审核通过，2：审核未通过", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/clients/stores/detail")
    public CommonResponse<CommonResult<List<StoreClientDetailVo>>> findAllStoreClientDetail(@RequestParam(value = "storeId", required = false) Integer storeId,
                                                                                            @RequestParam(value = "clientId", required = false) String clientId,
                                                                                            @RequestParam(value = "clientName", required = false) String clientName,
                                                                                            @RequestParam(value = "type", required = false) Byte type,
                                                                                            @RequestParam(value = "status", required = false) Byte status,
                                                                                            @RequestParam(value = "page") Integer page,
                                                                                            @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllStoreClientDetail(new StoreClientDetailVo(storeId, clientId, clientName, type, status), new PageVo(page, pageSize));
    }

    /**
     * 积分或提成提现审核接口
     * @param storeClientDetail
     * @return
     */
    @ApiOperation(value = "积分或提成提现审核")
    @ApiImplicitParam(name = "storeClientDetail", value = "id, userId, status必填，状态为1时必填bankAccountId，为2时必填remark", required = true, paramType = "body", dataType = "StoreClientDetail")
    @PutMapping(value = "/clients/withdraw/audit")
    public CommonResponse clientWithdrawAudit(@RequestBody StoreClientDetail storeClientDetail) {
        return clientService.clientWithdrawAudit(storeClientDetail);
    }

    //下级

    /**
     * 查询该客户的下级在各个店铺的消费情况和该客户的提成情况接口
     * @param id
     * @return
     */
    @ApiOperation(value = "查询该客户的下级在各个店铺的消费情况和该客户的提成情况")
    @ApiImplicitParam(name = "id", value = "客户编号", required = true, paramType = "query", dataType = "String")
    @GetMapping(value = "/clients/subordinate")
    public CommonResponse findSubordinateByInviterId(@RequestParam(value = "id") String id) {
        return clientService.findSubordinateByInviterId(new SubordinateVo(id));
    }

    /**
     * 查询该客户的某个下级在各个店铺的消费情况和该客户的提成情况
     * @param clientId
     * @return
     */
    @ApiOperation(value = "查询该客户的下级在各个店铺的消费情况和该客户的提成情况")
    @ApiImplicitParam(name = "clientId", value = "下级编号", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/clients/subordinate/{clientId}")
    public CommonResponse findSubordinateByClientId(@PathVariable(value = "clientId") String clientId) {
        return clientService.findSubordinateByClientId(new SubordinateVo(clientId));
    }
}
