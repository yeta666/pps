package com.yeta.pps.controller;

import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.ClientMembershipNumber;
import com.yeta.pps.service.ClientService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 客户相关接口
 * @author YETA
 * @date 2018/12/04/14:19
 */
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 新增会员卡号接口
     * @param clientMembershipNumber
     * @return
     */
    @PostMapping(value = "/clients/membershipNumbers")
    public CommonResponse addClientMembershipNumber(@RequestBody @Valid ClientMembershipNumber clientMembershipNumber) {
        return clientService.addClientMembershipNumber(clientMembershipNumber);
    }

    /**
     * 删除会员卡号接口
     * @param membershipNumberId
     * @return
     */
    @DeleteMapping(value = "/clients/membershipNumbers/{membershipNumberId}")
    public CommonResponse deleteClientMembershipNumber(@PathVariable(value = "membershipNumberId") Integer membershipNumberId) {
        return clientService.deleteClientMembershipNumber(new ClientMembershipNumber(membershipNumberId));
    }

    /**
     * 修改会员卡号接口
     * @param clientMembershipNumber
     * @return
     */
    @PutMapping(value = "/clients/membershipNumbers")
    public CommonResponse updateClientMembershipNumber(@RequestBody @Valid ClientMembershipNumber clientMembershipNumber) {
        return clientService.updateClientMembershipNumber(clientMembershipNumber);
    }

    /**
     * 查找所有会员卡号接口
     * @param number
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/clients/membershipNumbers")
    public CommonResponse findAllClientMembershipNumber(@RequestParam(value = "number", required = false) String number,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllClientMembershipNumber(new ClientMembershipNumber(number), new PageVo(page, pageSize));
    }

    /**
     * 根据id查找会员卡号接口
     * @param membershipNumberId
     * @return
     */
    @GetMapping(value = "/clients/membershipNumbers/{membershipNumberId}")
    public CommonResponse findClientMembershipNumberById(@PathVariable(value = "membershipNumberId") Integer membershipNumberId) {
        return clientService.findClientMembershipNumberById(new ClientMembershipNumber(membershipNumberId));
    }

    //

    /**
     * 新增客户级别接口
     * @param clientLevel
     * @return
     */
    @PostMapping(value = "/clients/levels")
    public CommonResponse addClientLevel(@RequestBody @Valid ClientLevel clientLevel) {
        return clientService.addClientLevel(clientLevel);
    }

    /**
     * 删除客户级别接口
     * @param levelId
     * @return
     */
    @DeleteMapping(value = "/clients/levels/{levelId}")
    public CommonResponse deleteClientLevel(@PathVariable(value = "levelId") Integer levelId) {
        return clientService.deleteClientLevel(new ClientLevel(levelId));
    }

    /**
     * 修改客户级别接口
     * @param clientLevel
     * @return
     */
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
    @GetMapping(value = "/clients/levels")
    public CommonResponse findAllClientLevel(@RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return clientService.findAllClientLevel(new PageVo(page, pageSize));
    }

    /**
     * 根据id查找客户级别接口
     * @param levelId
     * @return
     */
    @GetMapping(value = "/clients/levels/{levelId}")
    public CommonResponse findClientLevelById(@PathVariable(value = "levelId") Integer levelId) {
        return clientService.findClientLevelById(new ClientLevel(levelId));
    }

    //

    /**
     * 新增客户接口
     * @param clientVo
     * @return
     */
    @PostMapping(value = "/clients")
    public CommonResponse add(@RequestBody ClientVo clientVo) {
        return clientService.add(clientVo);
    }

    /**
     * 删除客户接口
     * @param clientId
     * @return
     */
    @DeleteMapping(value = "/clients/{clientId}")
    public CommonResponse delete(@PathVariable(value = "clientId") String clientId) {
        return clientService.delete(new ClientVo(clientId, null));
    }

    /**
     * 修改客户信息接口
     * @param clientVo
     * @return
     */
    @PutMapping(value = "/clients/info")
    public CommonResponse updateInfo(@RequestBody ClientVo clientVo) {
        return clientService.updateInfo(clientVo);
    }

    /**
     * 修改客户其他信息接口
     * @param clientVo
     * @return
     */
    @PutMapping(value = "/clients/other")
    public CommonResponse updateOther(@RequestBody ClientVo clientVo) {
        return clientService.updateOther(clientVo);
    }

    /**
     * 查找所有vip客户接口
     * @param id
     * @param name
     * @param phone
     * @param levelId
     * @param membershipNumber
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/clients/vip")
    public CommonResponse findAllVIPClient(@RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "phone", required = false) String phone,
                                  @RequestParam(value = "levelId", required = false) Integer levelId,
                                  @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                  @RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllVIPClient(new ClientVo(id, name, phone, levelId, membershipNumber), new PageVo(page, pageSize));
    }

    /**
     * 查找所有普通客户接口
     * @param id
     * @param name
     * @param phone
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/clients/common")
    public CommonResponse findAllCommonClient(@RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "phone", required = false) String phone,
                                  @RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return clientService.findAllCommonClient(new ClientVo(id, name, phone), new PageVo(page, pageSize));
    }

    /**
     * 根据id查找vip客户接口
     * @param clientId
     * @return
     */
    @GetMapping(value = "/clients/vip/{clientId}")
    public CommonResponse findVIPClientById(@PathVariable(value = "clientId") String clientId) {
        return clientService.findVIPClientById(new ClientVo(clientId, null));
    }

    /**
     * 根据id查找普通客户接口
     * @param clientId
     * @return
     */
    @GetMapping(value = "/clients/common/{clientId}")
    public CommonResponse findCommonClientById(@PathVariable(value = "clientId") String clientId) {
        return clientService.findCommonClientById(new ClientVo(clientId, null));
    }
}
