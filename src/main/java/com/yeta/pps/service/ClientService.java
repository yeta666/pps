package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.StoreMapper;
import com.yeta.pps.po.Client;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.MembershipNumber;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.CommonUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.ClientIntegralDetailVo;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreIntegralVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 客户相关逻辑处理
 * @author YETA
 * @date 2018/12/04/13:51
 */
@Service
public class ClientService {

    @Autowired
    private MyClientMapper myClientMapper;

    /**
     * 客户登陆
     * @param clientVo
     * @param request
     * @return
     */
    public CommonResponse login(ClientVo clientVo, HttpServletRequest request) {
        //判断参数
        if (clientVo.getIdentifyingCode() == null || clientVo.getUsername() == null || clientVo.getPassword() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断验证码
        String iCode = clientVo.getIdentifyingCode().toUpperCase();
        HttpSession session = request.getSession();
        Object siCode = session.getAttribute("identifyingCode");
        if (siCode == null || !siCode.equals(iCode)) {
            return new CommonResponse(CommonResponse.CODE4, null, CommonResponse.MESSAGE4);
        }
        //判断用户名、密码
        Client client = myClientMapper.findByUsernameAndPassword(clientVo);
        if (client == null) {
            return new CommonResponse(CommonResponse.CODE5, null, CommonResponse.MESSAGE5);
        }
        //判断客户是否已禁用
        if (client.getDisabled() == 1) {
            return new CommonResponse(CommonResponse.CODE6, null, CommonResponse.MESSAGE6);
        }
        //判断客户是否已登陆
        String clientId = client.getId();
        ServletContext servletContext = session.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineClientIds");
        for (String onlineId: onlineIds) {
            if (onlineId.equals(clientId)) {
                return new CommonResponse(CommonResponse.CODE11, null, CommonResponse.MESSAGE11);
            }
        }
        //设置已登陆
        onlineIds.add(clientId);
        session.setAttribute("clientId", clientId);
        session.setMaxInactiveInterval(60 * 60);      //60分钟
        clientVo.setId(clientId);
        clientVo.setToken(CommonUtil.getMd5(clientId));        //token就是md5加密后的客户id
        return new CommonResponse(CommonResponse.CODE1, clientVo, CommonResponse.MESSAGE1);
    }

    /**
     * 注销
     * @param clientVo
     * @param request
     * @return
     */
    public CommonResponse logout(ClientVo clientVo, HttpServletRequest request) {
        //获取在线客户id
        HttpSession session = request.getSession();
        ServletContext servletContext = session.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineClientIds");
        //注销
        for (String onlineId : onlineIds) {
            if (onlineId.equals(clientVo.getId())) {
                onlineIds.remove(onlineId);
                break;
            }
        }
        session.invalidate();
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 判断会员卡号是否使用的方法
     * @param membershipNumber
     * @return false表示没有使用，true表示已经使用
     */
    public boolean judgeMembershipNumber(MembershipNumber membershipNumber) {
        boolean result = false;
        //查询所有已经使用的会员卡号
        List<MembershipNumber> used = myClientMapper.findUsedMembershipNumber();
        //判断会员卡号是否使用
        if (membershipNumber.getId() != null) {
            if (used.stream().filter(m -> m.getId().toString().equals(membershipNumber.getId().toString())).findFirst().isPresent()) {
                result = true;
            }
        }
        if (membershipNumber.getNumber() != null) {
            if (used.stream().filter(m -> m.getNumber().equals(membershipNumber.getNumber())).findFirst().isPresent()) {
                result = true;
            }
        }
        return result;
    }

    //会员卡号

    /**
     * 新增会员卡号
     * @param membershipNumber
     * @return
     */
    public CommonResponse addMembershipNumber(MembershipNumber membershipNumber) {
        membershipNumber.setDisabled((byte) 0);
        if (myClientMapper.addMembershipNumber(membershipNumber) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 删除会员卡号
     * @param membershipNumbers
     * @return
     */
    @Transactional
    public CommonResponse deleteMembershipNumber(List<MembershipNumber> membershipNumbers) {
        membershipNumbers.stream().forEach(membershipNumber -> {
            //判断会员卡号是否使用
            if (judgeMembershipNumber(membershipNumber)) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除会员卡号
            if (myClientMapper.deleteMembershipNumber(membershipNumber) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改会员卡号
     * @param membershipNumber
     * @return
     */
    public CommonResponse updateMembershipNumber(MembershipNumber membershipNumber) {
        //判断参数
        if (membershipNumber.getId() == null || membershipNumber.getDisabled() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断会员卡号是否使用
        if (judgeMembershipNumber(membershipNumber)) {
            throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
        }
        //修改会员卡号
        if (myClientMapper.updateMembershipNumber(membershipNumber) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有会员卡号
     * @param membershipNumber
     * @param pageVo
     * @return
     */
    public CommonResponse findAllMembershipNumber(MembershipNumber membershipNumber, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountMembershipNumber(membershipNumber) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<MembershipNumber> membershipNumbers = myClientMapper.findAllPagedMembershipNumber(membershipNumber, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("会员卡号", "number"));
        titles.add(new Title("是否停用", "disabled"));
        CommonResult commonResult = new CommonResult(titles, membershipNumbers, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    //客户级别

    /**
     * 新增客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse addClientLevel(ClientLevel clientLevel) {
        if (myClientMapper.addClientLevel(clientLevel) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除客户级别
     * @param clientLevels
     * @return
     */
    @Transactional
    public CommonResponse deleteClientLevel(List<ClientLevel> clientLevels) {
        //查询所有已经使用的客户级别
        List<ClientLevel> clientLevelList = myClientMapper.findUsedClientLevel();
        clientLevels.stream().forEach(clientLevel -> {
            //判断客户级别是否使用
            if (clientLevelList.stream().filter(c -> c.getId().toString().equals(clientLevel.getId().toString())).findFirst().isPresent()) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除客户级别
            if (myClientMapper.deleteClientLevel(clientLevel) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse updateClientLevel(ClientLevel clientLevel) {
        //判断参数
        if (clientLevel.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改客户级别
        if (myClientMapper.updateClientLevel(clientLevel) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有客户级别
     * @return
     */
    public CommonResponse findAllClientLevel(PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountClientLevel() * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<ClientLevel> clientLevels = myClientMapper.findAllPagedClientLevel(pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("客户级别", "name"));
            titles.add(new Title("级别价格类型", "priceType"));
            titles.add(new Title("级别默认价格", "price"));
            CommonResult commonResult = new CommonResult(titles, clientLevels, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<ClientLevel> clientLevels = myClientMapper.findAllClientLevel();
        return new CommonResponse(CommonResponse.CODE1, clientLevels, CommonResponse.MESSAGE1);
    }

    //客户

    /**
     * 新增客户
     * @param clientVo
     * @return
     */
    @Transactional
    public CommonResponse add(ClientVo clientVo) {
        //设置初始属性
        clientVo.setId(UUID.randomUUID().toString().replace("-", ""));
        String phone = clientVo.getPhone();
        clientVo.setUsername(phone);
        clientVo.setPassword(phone.substring(phone.length() - 4));
        clientVo.setCreateTime(new Date());
        clientVo.setDisabled((byte)0);
        //判断会员卡号是否存在
        MembershipNumber membershipNumber = new MembershipNumber(clientVo.getMembershipNumber());
        if (myClientMapper.findMembershipNumberByNumber(membershipNumber) == null) {
            return new CommonResponse(CommonResponse.CODE15, null, CommonResponse.MESSAGE15);
        }
        //判断会员卡号是否使用
        if (judgeMembershipNumber(membershipNumber)) {
            return new CommonResponse(CommonResponse.CODE15, null, CommonResponse.MESSAGE15);
        }
        //判断邀请人是否填写
        if (clientVo.getInviterId() != null || clientVo.getInviterPhone() != null) {
            //判断邀请人是否存在
            Client inviter = myClientMapper.findByIdOrPhone(new ClientVo(clientVo.getInviterId(), clientVo.getInviterPhone()));
            if (inviter == null) {
                return new CommonResponse(CommonResponse.CODE16, null, CommonResponse.MESSAGE16);
            }
            clientVo.setInviterId(inviter.getId());
        }
        //新增客户
        if (myClientMapper.add(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除客户
     * @param clientVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<ClientVo> clientVos) {
        clientVos.stream().forEach(clientVo -> {
            //删除客户
            if (myClientMapper.delete(clientVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //删除客户/积分明细关系
            myClientMapper.deleteIntegralDetailByClientId(new ClientIntegralDetailVo(null, clientVo.getId()));
            //删除店铺/积分关系
            myClientMapper.deleteStoreIntegralByClientId(new StoreIntegralVo(null, clientVo.getId()));
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 客户自己修改信息
     * @param clientVo
     * @return
     */
    public CommonResponse update(ClientVo clientVo) {
        //判断参数
        if (clientVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //客户自己修改信息
        if (myClientMapper.update(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户停用和备注
     * @param clientVo
     * @return
     */
    public CommonResponse updateDisabledAndRemark(ClientVo clientVo) {
        if (myClientMapper.updateDisabledAndRemark(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有客户
     * @return
     */
    public CommonResponse findAll(ClientVo clientVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCount(clientVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查找所有客户
        List<ClientVo> clientVos = myClientMapper.findAllPaged(clientVo, pageVo);
        //补上邀请人姓名
        List<ClientVo> vos = myClientMapper.findAll(new ClientVo());
        clientVos.stream().forEach(vo1 -> {
            Optional<ClientVo> optional = vos.stream().filter(vo2 -> vo2.getId().equals(vo1.getInviterId())).findFirst();
            if (optional.isPresent()) {
                vo1.setInviterName(optional.get().getName());
            }
        });
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("客户编号", "id"));
        titles.add(new Title("客户姓名", "name"));
        titles.add(new Title("电话", "phone"));
        titles.add(new Title("客户级别", "clientLevel.name"));
        titles.add(new Title("邀请人", "inviterName"));
        titles.add(new Title("会员卡号", "membershipNumber"));
        titles.add(new Title("生日", "birthday"));
        titles.add(new Title("地址", "address"));
        titles.add(new Title("邮编", "postcode"));
        titles.add(new Title("最近交易时间", "lastDealTime"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("是否停用", "disabled"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, clientVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 导出客户信息
     * @param clientVo
     * @param response
     * @throws IOException
     */
    public void exportClient(ClientVo clientVo, HttpServletResponse response) throws IOException {
        //根据筛选条件查找客户
        List<ClientVo> clientVos = myClientMapper.findAll(clientVo);
        //补上邀请人姓名
        List<ClientVo> vos = myClientMapper.findAll(new ClientVo());
        clientVos.stream().forEach(vo1 -> {
            Optional<ClientVo> optional = vos.stream().filter(vo2 -> vo2.getId().equals(vo1.getInviterId())).findFirst();
            if (optional.isPresent()) {
                vo1.setInviterName(optional.get().getName());
            }
        });
        ClientVo vo = clientVos.get(0);
        //备注
        String remark = "【筛选条件】" +
                "，客户编号：" + (clientVo.getId() == null ? "无" : vo.getId()) +
                "，客户姓名：" + (clientVo.getName() == null ? "无" : vo.getName()) +
                "，电话：" + (clientVo.getPhone() == null ? "无" : vo.getPhone()) +
                "，会员卡号：" + (clientVo.getMembershipNumber() == null ? "无" : vo.getMembershipNumber()) +
                "，客户级别：" + (clientVo.getLevelId() == null ? "无" : vo.getClientLevel().getName());
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]{
                "客户编号", "姓名", "电话", "客户级别", "邀请人", "会员卡号", "生日", "地址", "邮编", "最近交易时间", "创建时间", "是否停用", "备注"
        });
        //最后一个必填列列数
        int lastRequiredCol = -1;
        //数据行
        List<List<String>> dataRowCells = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        clientVos.stream().forEach(gVo -> {
            List<String> dataRowCell = new ArrayList<>();
            dataRowCell.add(gVo.getId());
            dataRowCell.add(gVo.getName());
            dataRowCell.add(gVo.getPhone());
            dataRowCell.add(gVo.getClientLevel().getName());
            dataRowCell.add(gVo.getInviterName());
            dataRowCell.add(gVo.getMembershipNumber());
            dataRowCell.add(gVo.getBirthday() == null ? "" : sdf1.format(gVo.getBirthday()));
            dataRowCell.add(gVo.getAddress());
            dataRowCell.add(gVo.getPostcode());
            dataRowCell.add(gVo.getLastDealTime() == null ? "" : sdf2.format(gVo.getLastDealTime()));
            dataRowCell.add(sdf2.format(gVo.getCreateTime()));
            dataRowCell.add(gVo.getDisabled().toString());
            dataRowCell.add(gVo.getRemark());
            dataRowCells.add(dataRowCell);
        });
        //文件名
        String fileName = "【客户导出】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        List<List<String>> titleRowCells = new ArrayList<>();
        titleRowCells.add(titleRowCell);
        List<List<List<String>>> dataRowCellss = new ArrayList<>();
        dataRowCellss.add(dataRowCells);
        CommonUtil.outputExcel(remark, titleRowCells, lastRequiredCol, dataRowCellss, fileName, response);
    }

    /**
     * 获取导入客户模版
     * @param response
     * @throws IOException
     */
    public void getImportClientTemplate(HttpServletResponse response) throws IOException {
        //备注
        String remark = "【导入备注】，只能增加行数，按照标题填写，不能增加其他列。" +
                "必填列已标红，其中客户级别、邀请人、会员卡号需要填写系统中已经存在的，否则会导致导入失败。" +
                "邀请人编号和邀请人电话只需选择填入一个就行。" +
                "电话、会员卡号要求不能重复";
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]{
                "姓名", "电话", "客户级别", "邀请人编号", "邀请人电话", "会员卡号", "生日", "地址", "邮编", "备注"
        });
        //最后一个必填列列数
        int lastRequiredCol = 5;
        //文件名
        String fileName = "【客户导入模版】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        List<List<String>> titleRowCells = new ArrayList<>();
        titleRowCells.add(titleRowCell);
        CommonUtil.outputExcel(remark, titleRowCells, lastRequiredCol, new ArrayList<>(), fileName, response);
    }

    /**
     * 导入客户
     * @param multipartFile
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @Transactional
    public CommonResponse importClient(MultipartFile multipartFile) throws IOException, ParseException {
        //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
        //创建一个工作表sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取数据
        for (int j = 3; j <= sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(j);
            ClientVo clientVo = new ClientVo();
            //设置初始属性
            clientVo.setId(UUID.randomUUID().toString().replace("-", ""));
            clientVo.setCreateTime(new Date());
            clientVo.setDisabled((byte) 0);

            String name = CommonUtil.getCellValue(row.getCell(0));
            if (name.equals("")) {
                throw new CommonException(CommonResponse.CODE3, "姓名必填");
            }
            clientVo.setName(name);

            String phone = CommonUtil.getCellValue(row.getCell(1));
            if (phone.equals("") || phone.length() <= 4) {
                throw new CommonException(CommonResponse.CODE3, "电话必填");
            }
            clientVo.setPhone(phone);
            clientVo.setUsername(phone);
            clientVo.setPassword(phone.substring(phone.length() - 4));

            //判断客户级别分店是否可用
            String levelName = CommonUtil.getCellValue(row.getCell(2));
            if (levelName.equals("")) {
                throw new CommonException(CommonResponse.CODE3, "客户级别必填");
            }
            //判断客户级别是否存在
            ClientLevel clientLevel = new ClientLevel(levelName);
            clientLevel = myClientMapper.findClientLevelByIdOrName(clientLevel);
            if (clientLevel == null) {
                throw new CommonException(CommonResponse.CODE17, CommonResponse.MESSAGE17);
            }
            clientVo.setLevelId(clientLevel.getId());

            String inviterId = CommonUtil.getCellValue(row.getCell(3));
            String inviterPhone = CommonUtil.getCellValue(row.getCell(4));
            if (inviterId.equals("") && inviterPhone.equals("")) {
                throw new CommonException(CommonResponse.CODE3, "邀请人编号、邀请人电话二选一必填");
            }
            //判断邀请人是否存在
            Client inviter = myClientMapper.findByIdOrPhone(new ClientVo(inviterId.equals("") ? null : inviterId, inviterPhone.equals("") ? null : inviterPhone));
            if (inviter == null) {
                throw new CommonException(CommonResponse.CODE16, CommonResponse.MESSAGE16);
            }
            clientVo.setInviterId(inviter.getId());

            String number = CommonUtil.getCellValue(row.getCell(5));
            if (number.equals("")) {
                throw new CommonException(CommonResponse.CODE3, "会员卡号必填");
            }
            //判断会员卡号是否存在
            MembershipNumber membershipNumber = new MembershipNumber(number);
            if (myClientMapper.findMembershipNumberByNumber(membershipNumber) == null) {
                throw new CommonException(CommonResponse.CODE15, CommonResponse.MESSAGE15);
            }
            //判断会员卡号是否使用
            if (judgeMembershipNumber(membershipNumber)) {
                throw new CommonException(CommonResponse.CODE15, CommonResponse.MESSAGE15);
            }
            clientVo.setMembershipNumber(number);

            String birthday = CommonUtil.getCellValue(row.getCell(6));
            if (!birthday.equals("")) {
                clientVo.setBirthday(sdf.parse(birthday));
            }

            clientVo.setAddress(CommonUtil.getCellValue(row.getCell(9)));
            clientVo.setPostcode(CommonUtil.getCellValue(row.getCell(10)));
            clientVo.setRemark(CommonUtil.getCellValue(row.getCell(13)));

            //新增客户
            if (myClientMapper.add(clientVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //店铺/积分关系

    /**
     * 根据条件查询店铺积分关系
     * @param storeIntegralVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllStoreIntegral(StoreIntegralVo storeIntegralVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountStoreIntegral(storeIntegralVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //根据条件查询店铺积分关系
        List<StoreIntegralVo> storeIntegralVos = myClientMapper.findAllPagedStoreIntegral(storeIntegralVo, pageVo);
        //补上邀请人姓名
        List<ClientVo> vos = myClientMapper.findAll(new ClientVo());
        storeIntegralVos.stream().forEach(vo1 -> {
            Optional<ClientVo> optional = vos.stream().filter(vo2 -> vo2.getId().equals(vo1.getClientVo().getInviterId())).findFirst();
            if (optional.isPresent()) {
                vo1.getClientVo().setInviterName(optional.get().getName());
            }
        });
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("积分", "integral"));
        if (storeIntegralVo.getClientId() != null && storeIntegralVo.getStoreId() == null) {        //客户自己查询在每个店铺的积分
            titles.add(new Title("店铺编号", "store.id"));
            titles.add(new Title("店铺名", "store.name"));
            titles.add(new Title("店铺地址", "store.address"));
        } else if (storeIntegralVo.getClientId() == null && storeIntegralVo.getStoreId() != null) {     //店铺查询在本店有积分的客户
            titles.add(new Title("客户编号", "clientVo.id"));
            titles.add(new Title("客户姓名", "clientVo.name"));
            titles.add(new Title("电话", "clientVo.phone"));
            titles.add(new Title("客户级别", "clientVo.clientLevel.name"));
            titles.add(new Title("邀请人", "clientVo.inviterName"));
            titles.add(new Title("会员卡号", "clientVo.membershipNumber"));
            titles.add(new Title("生日", "clientVo.birthday"));
            titles.add(new Title("地址", "clientVo.address"));
            titles.add(new Title("邮编", "clientVo.postcode"));
            titles.add(new Title("最近交易时间", "clientVo.lastDealTime"));
            titles.add(new Title("创建时间", "clientVo.createTime"));
            titles.add(new Title("是否停用", "clientVo.disabled"));
            titles.add(new Title("备注", "clientVo.remark"));
        }
        CommonResult commonResult = new CommonResult(titles, storeIntegralVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    //客户/积分明细

    /**
     * 根据条件查询客户/积分明细
     * @param clientIntegralDetailVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllIntegralDetail(ClientIntegralDetailVo clientIntegralDetailVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountIntegralDetail(clientIntegralDetailVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //根据条件查询客户/积分明细
        List<ClientIntegralDetailVo> clientIntegralDetailVos = myClientMapper.findAllPagedIntegralDetail(clientIntegralDetailVo, pageVo);
        //补上邀请人姓名
        List<ClientVo> vos = myClientMapper.findAll(new ClientVo());
        clientIntegralDetailVos.stream().forEach(vo1 -> {
            Optional<ClientVo> optional = vos.stream().filter(vo2 -> vo2.getId().equals(vo1.getClientVo().getInviterId())).findFirst();
            if (optional.isPresent()) {
                vo1.getClientVo().setInviterName(optional.get().getName());
            }
        });
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("操作类型", "type"));
        titles.add(new Title("改变积分", "changeIntegral"));
        titles.add(new Title("改变后的积分", "afterChangeIntegral"));
        titles.add(new Title("单据编号", "orderId"));
        if (clientIntegralDetailVo.getClientId() != null && clientIntegralDetailVo.getStoreId() == null) {        //客户自己查询在每个店铺的积分明细
            titles.add(new Title("店铺编号", "store.id"));
            titles.add(new Title("店铺名", "store.name"));
            titles.add(new Title("店铺地址", "store.address"));
        } else if (clientIntegralDetailVo.getClientId() == null && clientIntegralDetailVo.getStoreId() != null) {     //店铺查询在本店有积分明细的客户
            titles.add(new Title("客户编号", "clientVo.id"));
            titles.add(new Title("客户姓名", "clientVo.name"));
            titles.add(new Title("电话", "clientVo.phone"));
            titles.add(new Title("客户级别", "clientVo.clientLevel.name"));
            titles.add(new Title("邀请人", "clientVo.inviterName"));
            titles.add(new Title("会员卡号", "clientVo.membershipNumber"));
            titles.add(new Title("生日", "clientVo.birthday"));
            titles.add(new Title("地址", "clientVo.address"));
            titles.add(new Title("邮编", "clientVo.postcode"));
            titles.add(new Title("最近交易时间", "clientVo.lastDealTime"));
            titles.add(new Title("创建时间", "clientVo.createTime"));
            titles.add(new Title("是否停用", "clientVo.disabled"));
            titles.add(new Title("备注", "clientVo.remark"));
        }
        CommonResult commonResult = new CommonResult(titles, clientIntegralDetailVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }
}
