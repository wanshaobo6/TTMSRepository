package com.ttms.service.SystemManage;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.SysMenus;
import com.ttms.Entity.SysRoleMenus;
import com.ttms.Entity.SysRoles;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.SysMenusMapper;
import com.ttms.Mapper.SysRoleMenusMapper;
import com.ttms.Mapper.SysRolesMapper;
import com.ttms.Mapper.SysUserMapper;
import com.ttms.TTMSApplication;
import com.ttms.utils.CodecUtils;
import com.ttms.utils.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class SysMenusService {
    @Autowired
    private SysMenusMapper sysMenusMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMenusMapper sysRoleMenusMapper;
    @Autowired
    private SysRolesMapper sysRolesMapper;
    /**
     * 功能描述: <br>根据pid查询sysmenu
     * 〈〉
     * @Param: [id]
     * @Return: java.util.List<com.ttms.Entity.SysMenus>
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public List<SysMenus> getSysMenusByPid(int id){
        SysMenus sysMenus = new SysMenus();
        sysMenus.setParentid(id);
        List<SysMenus> sysMenusList = sysMenusMapper.select(sysMenus);
        return sysMenusList;
    }

    /**
     * 查询sysMenus树
     * @return
     */
    public List<SysMenus> getSysMenusTree(){
        List<SysMenus> parentSysMenus = getSysMenusByPid(0);
        getSysMensByPidRecursive(parentSysMenus);
        return parentSysMenus;
    }

    /**
     * 功能描述: <br> 递归查询子菜单
     * 〈〉
     * @Param: [sysMenuList]
     * @Return: java.util.List<com.ttms.Entity.SysMenus>
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public List<SysMenus> getSysMensByPidRecursive(List<SysMenus> sysMenuList){
        if(sysMenuList == null || sysMenuList.size() ==0)
            return sysMenuList;
        for (SysMenus sysMenu:sysMenuList){
            List<SysMenus> childMenus = getSysMenusByPid(sysMenu.getId());
            sysMenu.setChildMenus(getSysMensByPidRecursive(childMenus));
        }
        return sysMenuList;
    }

    /**
     * 功能描述: <br>获取所有拦截的url 和 需要给的permission
     * 〈〉
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: 万少波
     * @Date: 2019/5/26 12:46
     */
    public Map<String,String> getUrlPermissionMapping(){
        List<SysMenus> sysMenuTree = getSysMenusTree();
        HashMap<String, String> urlPermissionsMapping = new HashMap<>();
        Stack<String> urlStack = new Stack<>();
        Stack<String>  permissionStack= new Stack<>();
        getUrlPermissionMappingRecursive(urlPermissionsMapping,urlStack,permissionStack,sysMenuTree);
        return urlPermissionsMapping;
    }


    /**
     * 功能描述: <br>递归获取url --> Mapping映射s
     * 〈〉
     * @Param: [urlPermissionsMapping, urlStack, permissionStack, sysMenusList]
     * @Return: void
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public void getUrlPermissionMappingRecursive(Map<String,String> urlPermissionsMapping ,
                                                 Stack<String> urlStack ,
                                                 Stack<String> permissionStack,
                                                 List<SysMenus> sysMenusList){
        if(CollectionUtils.isEmpty(sysMenusList))
            return;
        for (SysMenus sysMenu : sysMenusList) {
            //获取子菜单
            List<SysMenus> childMenus = sysMenu.getChildMenus();
            //将当前子菜单urlStack 和 permissionStack 压栈
            urlStack.push(sysMenu.getUrl());
            permissionStack.push(sysMenu.getPermission());
            if(CollectionUtils.isEmpty(childMenus)){
                //当前为子结点  /添加映射
                addUrlPermissionMapping(urlPermissionsMapping , urlStack , permissionStack);
            }else{
                //当前不为子结点
                getUrlPermissionMappingRecursive(urlPermissionsMapping,urlStack,permissionStack,childMenus);
            }
            //urlStack 和 permissionStack 出栈
            urlStack.pop();
            permissionStack.pop();
        }
    }

    /**
     * 功能描述: <br>添加url permission到Map
     * 〈〉
     * @Param: [urlPermissionsMapping, urlStack, permissionStack]
     * @Return: void
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    private void addUrlPermissionMapping(Map<String, String> urlPermissionsMapping, Stack<String> urlStack, Stack<String> permissionStack) {
        StringBuffer urlBuffer = new StringBuffer();
        for (String surl: urlStack) {
            urlBuffer.append("/"+surl);
        }
        urlBuffer.append("/**");


        StringBuffer permissionBuffer = new StringBuffer();
        permissionBuffer.append("authc,perms[");
        for (String spermission: permissionStack) {
            permissionBuffer.append(spermission+":");
        }
        permissionBuffer.setCharAt(permissionBuffer.length()-1,']');
        urlPermissionsMapping.put(urlBuffer.toString(),permissionBuffer.toString());
    }
    /*功能描述
     *@author罗占
     *@Description
     *Date15:09 2019/5/26
     *Param
     *return
     **/
    public PageResult<SysUser> queryUserByPage(Integer page,Integer rows,String name){
        PageHelper.startPage(page,rows);
        Example example = new Example(SysUser.class);
        if(StringUtils.isNotBlank(name)){
            example.createCriteria().andLike("name","%"+name+"%");
        }
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new TTMSException(ExceptionEnum.USER_NOT_FOUND);
        }
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getList());
        PageResult<SysUser> result=new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setTotalPage(pageInfo.getPages());
        result.setItems(pageInfo.getList());
        return result;
    }

    /*
     *功能描述：根据id查询用户
     *@author罗占
     *@Description
     *Date15:36 2019/5/26
     *Param
     *return
     **/
    public SysUser getUserById(Integer id){
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        if(user == null){
            throw new TTMSException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }

    /**
     * 功能描述: <br>根据用户名查询用户
     * 〈〉
     * @Param: [username]
     * @Return: com.ttms.Entity.SysUser
     * @Author: 万少波
     * @Date: 2019/5/26 17:39
     */
    public SysUser getUserByUserName(String username){
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        List<SysUser> users = sysUserMapper.select(sysUser);
        if(!CollectionUtils.isEmpty(users))
            return users.get(0);
        else
            return null;
    }
    /*
     *功能描述：删除用户
     *@author罗占
     *@Description
     *Date16:50 2019/5/26
     *Param
     *return
     **/
    public void deleteUserById( Integer id){
        int i =  this.sysUserMapper.deleteByPrimaryKey(id);
        if(i != 0 ){
            throw new TTMSException(ExceptionEnum.USER_DELETE_FAIL);
        }
    }

    /**
     * 功能描述: 查询所有角色
     * 〈〉
     * @Param: []
     * @Return: java.util.List<com.ttms.Entity.SysRoles>
     * @Author: 吴彬
     * @Date: 15:49 15:49
     */
    public List<SysRoles> getAllRoles() {
        List<SysRoles> list = this.sysRolesMapper.selectAll();
        if(CollectionUtils.isEmpty(list)){
            throw new TTMSException(ExceptionEnum.NOT_FOUND_ROLERS);
        }
        return list;
    }
    /**
     * @Description:    分页查询所有角色
     * @param
     * @Author:         吴彬
     * @UpdateRemark:   修改内容
     * @Version:        1.0
     */
    public PageResult<SysRoles> getRolesByPage(Integer page, Integer rows, String name) {
        PageHelper.startPage(page, rows);
        Example e=new Example(SysRoles.class);
        if(StringUtils.isNotBlank(name)){
            e.createCriteria().andLike(name, "%"+name+"%");
        }
        List<SysRoles> sysRoles = this.sysRolesMapper.selectByExample(e);
        if(CollectionUtils.isEmpty(sysRoles)){
            throw new TTMSException(ExceptionEnum.NOT_FOUND_ROLERS);
        }

        PageInfo<SysRoles> list=new PageInfo<>(sysRoles);
        PageResult<SysRoles> result = new PageResult<SysRoles>();
        result.setItems(list.getList());
        result.setTotal(list.getTotal());
        result.setTotalPage(list.getPages());
        return result;
    }
    /**
     * 功能描述:添加角色和分配权限
     * 〈〉
     * @Param: [name, note, menuIds, username]
     * @Return: void
     * @Author: 吴彬
     * @Date: 17:32 17:32
     */
    @Transactional
    public void AddRole(String name, String note, List<Integer> menuIds, String username) {
        //添加角色
        SysRoles role=new SysRoles();
        role.setName(name);
        role.setNote(note);
        role.setCreatedtime(new Date());
        role.setCreateduser(username);
        role.setModifiedtime(null);
        int i = this.sysRolesMapper.insert(role);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.INSERT_ROLERS_FAIL);
        }
        //添加角色和菜单的关联表
        addRoleAndMenus(role,menuIds);

    }

    private void addRoleAndMenus(SysRoles role, List<Integer> menuIds) {
        SysRoleMenus roleMenus = null;
        for (Integer id : menuIds) {
            roleMenus = new SysRoleMenus();
            String s = String.valueOf(role.getId());
            roleMenus.setRoleId(Integer.parseInt(s));
            roleMenus.setMenuId(id);
            int i = this.sysRoleMenusMapper.insert(roleMenus);
            if (i != 1) {
                throw new TTMSException(ExceptionEnum.INSERT_ROLERS_FAIL);
            }

        }
    }

        /**
         * 功能描述: 新增用户
         * 〈〉
         * @Param: [user]
         * @Return: void
         * @Author: lhf
         * @Date: 2019/5/26 17:40
         */
        public void addSysUser( String username,  String image,
                                String password,  String mail,
                                String  phonenumber) {
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setImage(image);
            user.setSalt(CodecUtils.generateSalt());
            password = CodecUtils.md5Hex(password, user.getSalt());
            user.setPassword(password);
            user.setEmail(mail);
            user.setMobile(phonenumber);
            user.setValid((byte) 0);
            Date now = new Date();
            user.setCreatedtime(now);
            user.setModifiedtime(now);
            //设置修改用户
            int i = this.sysUserMapper.insert(user);
            if (i != 1) {
                throw new TTMSException(ExceptionEnum.USER_ADD_FAILURE);
            }
        }

        /**
         * 功能描述: 修改用户
         * 〈〉
         * @Param: [id]
         * @Return: com.ttms.Entity.SysUser
         * @Author: lhf
         * @Date: 2019/5/26 19:39
         */
        public void updateUserById(SysUser sysUser) {
            int count = this.sysUserMapper.updateByPrimaryKey(sysUser);
            if (count == 1) {
                throw new TTMSException(ExceptionEnum.USER_UPDATE_FAILURE);
            }
        }

        /**
         * 功能描述: 启用和禁用用户
         * 〈〉
         * @Param: [id]
         * @Return: void
         * @Author: lhf
         * @Date: 2019/5/26 17:40
         */
        public void validOrInvalid(Integer id) {
            SysUser user = findUserById(id);
            System.out.println("user = " + user);
       /* int vid = ;
        vid = vid ^ 1;*/
            //vid = 1- vid;
            user.setValid((byte) (user.getValid() ^ 1));
            int i = this.sysUserMapper.updateByPrimaryKey(user);
            if (i != 1) {
                throw new TTMSException(ExceptionEnum.USER_VALID_MODIFY_ERROR);
            }
        }

        /**
         * 功能描述: 查找用户
         * 〈〉
         * @Param: [id]
         * @Return: com.ttms.Entity.SysUser
         * @Author: lhf
         * @Date: 2019/5/26 17:41
         */
        public SysUser findUserById(Integer id) {
            SysUser user = this.sysUserMapper.selectByPrimaryKey(id);
            if (user == null) {
                throw new TTMSException(ExceptionEnum.USER_NOT_EXIST);
            }
            return user;
        }
}