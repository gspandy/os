package n.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import n.entity.authc.Permission;
import n.entity.authc.Role;
import n.service.authc.IPermissionService;
import n.service.authc.IRoleService;
import n.table.dto.authc.MenuTreeDTO;
import n.web.test.BaseTest;

public class PermissionServiceTest extends BaseTest{

	@Resource
	private IPermissionService permissionService;
	@Resource
	private IRoleService roleService;
	
	
	@Test
	public void delete() throws Exception{
		
		permissionService.delete(31);
		
		Permission p = permissionService.find(31);
		System.out.println(JSON.toJSONString(p));
		
		
	}
	
	@Test
	public void menuTreeTest() throws Exception{
		List<Permission> p = permissionService.findByUserId(1l);
		List<MenuTreeDTO> menuList = new ArrayList<MenuTreeDTO>();
		menuList = menuTree(p);
		String res = JSON.toJSONString(menuList);
		System.err.println(res);
		
		
	}
	
	/**
	 * 菜单树
	 */
	private List<MenuTreeDTO> menuTree(List<Permission> list) {
		List<MenuTreeDTO> menuTreeList = new ArrayList<MenuTreeDTO>();
		return generateMenuTree(list, menuTreeList, null, 1);
	}

	private List<MenuTreeDTO> generateMenuTree(List<Permission> allList, List<MenuTreeDTO> dtoList,
			Integer parentPermissionId, Integer floor) {
		if (allList == null || allList.size() == 0)
			return dtoList;

		for (Permission p : allList) {
			if (p.getPermissionType() == 2 && p.getFloor() == floor) {
				MenuTreeDTO pt = new MenuTreeDTO();
				pt.setId(p.getId());
				pt.setName(p.getPermissionName());
				pt.setDeep(p.getDeep());
				pt.setPath(p.getPath());
				pt.setIcon(p.getIcon());
				pt.setIsDisplay(p.getIsDisplay());

				// 递归查找子权限
				List<MenuTreeDTO> subTreeList = generateMenuTree(allList, new ArrayList<MenuTreeDTO>(), p.getId(),
						null);
				pt.setSubList(subTreeList);
				dtoList.add(pt);
			} else if (p.getPermissionType() == 2 && p.getParentPermissionId() == parentPermissionId) {
				MenuTreeDTO pt = new MenuTreeDTO();
				pt.setId(p.getId());
				pt.setName(p.getPermissionName());
				pt.setDeep(p.getDeep());
				pt.setPath(p.getPath());
				pt.setIsDisplay(p.getIsDisplay());

				// 递归查找子权限
				List<MenuTreeDTO> subTreeList = generateMenuTree(allList, new ArrayList<MenuTreeDTO>(), p.getId(),
						null);
				pt.setSubList(subTreeList);

				dtoList.add(pt);
			}
		}

		return dtoList;

	}
	
	
	
	
	
	
	@Test
	public void findByIn(){
		String s = "chat-manage/page";
		String[] ps = s.split(",");
		List<String> plist = Arrays.asList(ps);
		for (Permission p: permissionService.findByCodeIn(plist)){
			System.err.println(p.getPermissionName());
		}
	}
	
	/**
	 * 初始化角色权限数据
	 * @throws Exception
	 */
	@Test
	public void addTest() throws Exception{
		Collection<Permission> list = per();
		Role role = roleService.findByRoleName("administrator");
		if(role != null && permissionService.findAll().size() == 0){
			for (Permission permission : list) {
				permission = permissionService.save(permission);
				permissionService.insertRolePermission(role.getId(), permission.getId());
			}
			System.err.println("初始化administrator权限数据完成！");
		}
	}
	public enum PermissionType{
		root,
		permission,
		menu
	}
	@Enumerated(EnumType.STRING)
	public PermissionType d;
	@Test
	public void mainTest() throws Exception{
//		String str = "1,2,3";
//		System.err.println(str.contains("4"));
//		System.err.println(permissionService.deleteByRoleId(new Integer(2)));
//		List<Permission> ls = findByRoleId();
//		List<Permission> ls = findByUserId();
//		for (Permission p : ls) {
//			System.err.println("【权限代码：】"+p.getCode()+" 【权限名称：】"+p.getPermissionName()+"   【父ID：】"+p.getParentPermissionId());
//		}
		d = PermissionType.permission;
		System.err.println(d);
//		setStringTest();
//		List<Map<String, Object>> list = permissionService.getBackPermissionList();
//		for (Map<String, Object> map : list) {
//			System.err.println(map.get("id"));
//			List<Map<String,Object>> object = (List<Map<String, Object>>) map.get("children");
//			if(null != object && object.size() > 0){
//				for (Map<String, Object> m : object) {
//					System.err.println(m.get("id"));
//				}
//			}
//		}
	}
	/**
	 * 查询用户拥有的权限
	 * @return
	 * @throws Exception
	 */
	private List<Permission> findByUserId() throws Exception{
		List<Permission> ls = permissionService.findByUserId(1L);
		return ls;
	}
	/**
	 * 查询角色拥有的权限
	 * @return
	 * @throws Exception
	 */
	private List<Permission> findByRoleId() throws Exception{
		List<Permission> ls = new ArrayList<>();
		ls =permissionService.findByRoleId(2);
		return ls;
	}
	/**
	 * shiro权限代码code集测试
	 */
	private void setStringTest(){
		Set<String> permissions = permissionService.findUserPermissionByUID(3L);
		for(String p : permissions){
			System.err.println(p);
		}
	}
	/**
	 * 添加权限
	 * @return
	 */
	private Collection<Permission> per(){
		//添加权限
		List<Permission> ls = new ArrayList<>();
		Permission p = new Permission();
		Permission p0 = new Permission();
		Permission p1 = new Permission();
		Permission p2 = new Permission();
		Permission p3 = new Permission();
		Permission p4 = new Permission();
		Permission p5 = new Permission();
		Permission p6 = new Permission();
		Permission p7 = new Permission();
		Permission p8 = new Permission();
		Permission p9 = new Permission();
		Permission p10 = new Permission();
		Permission p11 = new Permission();
		Permission p12 = new Permission();
		Permission p13 = new Permission();
		Permission p14 = new Permission();
		Permission p15 = new Permission();
		Permission p16 = new Permission();
		Permission p17 = new Permission();
		Permission p18 = new Permission();
		Permission p19 = new Permission();
		Permission p20 = new Permission();
		Permission p21 = new Permission();
		Permission p22 = new Permission();
		Permission p23 = new Permission();
		Permission p24 = new Permission();
		Permission p25 = new Permission();
		Permission p26 = new Permission();
		Permission p27 = new Permission();
	p.setId(1);
	p.setCode("*");
	p.setPermissionName("根目录");
	p.setPermissionType(0);//权限类型
	p.setPath("chat-root");
	p.setParentPermissionId(0);
	p.setDeep(0);//从0开始
	p.setFloor(0);//层级从1开始
	ls.add(p);
		p0.setId(2);
		p0.setCode("chat-manage");
		p0.setPermissionName("会话管理");
		p0.setPermissionType(1);//权限类型
		p0.setPath("chat-manage");
		p0.setParentPermissionId(1);
		p0.setDeep(1);//从0开始
		p0.setFloor(1);//层级从1开始
		ls.add(p0);
			p3.setId(9);
			p3.setCode("chat-manage/normal");
			p3.setPermissionName("与访客对话");
			p3.setPermissionType(1);//权限类型
			p3.setPath("chat-manage/normal");
			p3.setParentPermissionId(2);
			p3.setDeep(1);//从0开始
			p3.setFloor(2);//层级从1开始
			ls.add(p3);
			
			p4.setId(10);
			p4.setCode("chat-manage/close");
			p4.setPermissionName("关闭对话");
			p4.setPermissionType(1);//权限类型
			p4.setPath("chat-manage/close");
			p4.setParentPermissionId(2);
			p4.setDeep(2);//从0开始
			p4.setFloor(2);//层级从1开始
			ls.add(p4);
			
			p5.setId(11);
			p5.setCode("chat-manage/delete");
			p5.setPermissionName("删除会话信息");
			p5.setPermissionType(1);//权限类型
			p5.setPath("chat-manage/delete");
			p5.setParentPermissionId(2);
			p5.setDeep(3);//从0开始
			p5.setFloor(2);//层级从1开始
			ls.add(p5);
			
			p13.setId(12);
			p13.setCode("chat-manage/auto");
			p13.setPermissionName("主动对话");
			p13.setPermissionType(1);//权限类型
			p13.setPath("chat-manage/auto");
			p13.setParentPermissionId(2);
			p13.setDeep(4);//从0开始
			p13.setFloor(2);//层级从1开始
			ls.add(p13);
			
			p14.setId(13);
			p14.setCode("chat-manage/page");
			p14.setPermissionName("网页对话");
			p14.setPermissionType(1);//权限类型
			p14.setPath("chat-manage/page");
			p14.setParentPermissionId(2);
			p14.setDeep(5);//从0开始
			p14.setFloor(2);//层级从1开始
			ls.add(p14);
		
		p1.setId(3);
		p1.setCode("services-manage");
		p1.setPermissionName("客服管理");
		p1.setPermissionType(1);//权限类型
		p1.setPath("services-manage");
		p1.setParentPermissionId(1);
		p1.setDeep(2);//从0开始
		p1.setFloor(1);//层级从1开始
		ls.add(p1);
			p6.setId(14);
			p6.setCode("services-manage/create");
			p6.setPermissionName("增加客服");
			p6.setPermissionType(1);//权限类型
			p6.setPath("services-manage/create");
			p6.setParentPermissionId(3);
			p6.setDeep(1);//从0开始
			p6.setFloor(2);//层级从1开始
			ls.add(p6);
			
			p7.setId(15);
			p7.setCode("services-manage/update");
			p7.setPermissionName("修改客服");
			p7.setPermissionType(1);//权限类型
			p7.setPath("services-manage/update");
			p7.setParentPermissionId(3);
			p7.setDeep(2);//从0开始
			p7.setFloor(2);//层级从1开始
			ls.add(p7);
			
			p8.setId(16);
			p8.setCode("services-manage/delete");
			p8.setPermissionName("删除客服");
			p8.setPermissionType(1);//权限类型
			p8.setPath("services-manage/delete");
			p8.setParentPermissionId(3);
			p8.setDeep(3);//从0开始
			p8.setFloor(2);//层级从1开始
			ls.add(p8);
			
			p9.setId(17);
			p9.setCode("services-manage/forbidden");
			p9.setPermissionName("禁用客服");
			p9.setPermissionType(1);//权限类型
			p9.setPath("services-manage/forbidden");
			p9.setParentPermissionId(3);
			p9.setDeep(4);//从0开始
			p9.setFloor(2);//层级从1开始
			ls.add(p9);
			
			p15.setId(18);
			p15.setCode("services-manage/view");
			p15.setPermissionName("查看其他客服列表");
			p15.setPermissionType(1);//权限类型
			p15.setPath("services-manage/view");
			p15.setParentPermissionId(3);
			p15.setDeep(5);//从0开始
			p15.setFloor(2);//层级从1开始
			ls.add(p15);
		
		p2.setId(4);
		p2.setCode("dialog-manage");
		p2.setPermissionName("对话窗口管理");
		p2.setPermissionType(1);//权限类型
		p2.setPath("dialog-manage");
		p2.setParentPermissionId(1);
		p2.setDeep(3);//从0开始
		p2.setFloor(1);//层级从1开始
		ls.add(p2);
			p10.setId(19);
			p10.setCode("dialog-manage/promcontent");
			p10.setPermissionName("编辑提示内容");
			p10.setPermissionType(1);//权限类型
			p10.setPath("dialog-manage/promcontent");
			p10.setParentPermissionId(4);
			p10.setDeep(1);//从0开始
			p10.setFloor(2);//层级从1开始
			ls.add(p10);
			
			p11.setId(20);
			p11.setCode("dialog-manage/inputcontent");
			p11.setPermissionName("编辑快速输入内容");
			p11.setPermissionType(1);//权限类型
			p11.setPath("dialog-manage/inputcontent");
			p11.setParentPermissionId(4);
			p11.setDeep(2);//从0开始
			p11.setFloor(2);//层级从1开始
			ls.add(p11);
			
			p12.setId(21);
			p12.setCode("dialog-manage/timeout-promt");
			p12.setPermissionName("开启访客超时提醒");
			p12.setPermissionType(1);//权限类型
			p12.setPath("dialog-manage/timeout-promt");
			p12.setParentPermissionId(4);
			p12.setDeep(3);//从0开始
			p12.setFloor(2);//层级从1开始
			ls.add(p12);
		p16.setId(5);
		p16.setCode("ip-manage");
		p16.setPermissionName("IP阻止");
		p16.setPermissionType(1);//权限类型
		p16.setPath("ip-manage");
		p16.setParentPermissionId(1);
		p16.setDeep(4);//从0开始
		p16.setFloor(1);//层级从1开始
		ls.add(p16);
			p19.setId(22);
			p19.setCode("ip-manage/update");
			p19.setPermissionName("IP阻止更改");
			p19.setPermissionType(1);//权限类型
			p19.setPath("ip-manage/update");
			p19.setParentPermissionId(5);
			p19.setDeep(1);//从0开始
			p19.setFloor(2);//层级从1开始
			ls.add(p19);
		
		p17.setId(6);
		p17.setCode("chat-record");
		p17.setPermissionName("对话记录");
		p17.setPermissionType(1);//权限类型
		p17.setPath("chat-record");
		p17.setParentPermissionId(1);
		p17.setDeep(5);//从0开始
		p17.setFloor(1);//层级从1开始
		ls.add(p17);
			p20.setId(23);
			p20.setCode("chat-record/view");
			p20.setPermissionName("查看自己的对话记录");
			p20.setPermissionType(1);//权限类型
			p20.setPath("ip-manage/view");
			p20.setParentPermissionId(6);
			p20.setDeep(1);//从0开始
			p20.setFloor(2);//层级从1开始
			ls.add(p20);
		
		p18.setId(7);
		p18.setCode("leave-message");
		p18.setPermissionName("访客留言");
		p18.setPermissionType(1);//权限类型
		p18.setPath("leave-message");
		p18.setParentPermissionId(1);
		p18.setDeep(6);//从0开始
		p18.setFloor(1);//层级从1开始
		ls.add(p18);
			p21.setId(24);
			p21.setCode("leave-message/view");
			p21.setPermissionName("查看访客留言");
			p21.setPermissionType(1);//权限类型
			p21.setPath("leave-message/view");
			p21.setParentPermissionId(7);
			p21.setDeep(1);//从0开始
			p21.setFloor(2);//层级从1开始
			ls.add(p21);
			p22.setId(25);
			p22.setCode("leave-message/reply");
			p22.setPermissionName("回复访客留言");
			p22.setPermissionType(1);//权限类型
			p22.setPath("leave-message/reply");
			p22.setParentPermissionId(7);
			p22.setDeep(2);//从0开始
			p22.setFloor(2);//层级从1开始
			ls.add(p22);
		p23.setId(8);
		p23.setCode("company-manage");
		p23.setPermissionName("公司管理");
		p23.setPermissionType(1);//权限类型
		p23.setPath("company-manage");
		p23.setParentPermissionId(1);
		p23.setDeep(7);//从0开始
		p23.setFloor(1);//层级从1开始
		ls.add(p23);
			p24.setId(26);
			p24.setCode("company-manage/create");
			p24.setPermissionName("增加公司");
			p24.setPermissionType(1);//权限类型
			p24.setPath("company-manage/create");
			p24.setParentPermissionId(8);
			p24.setDeep(1);//从0开始
			p24.setFloor(2);//层级从1开始
			ls.add(p24);
			p25.setId(27);
			p25.setCode("company-manage/update");
			p25.setPermissionName("修改公司信息");
			p25.setPermissionType(1);//权限类型
			p25.setPath("company-manage/update");
			p25.setParentPermissionId(8);
			p25.setDeep(2);//从0开始
			p25.setFloor(2);//层级从1开始
			ls.add(p25);
			p26.setId(28);
			p26.setCode("company-manage/delete");
			p26.setPermissionName("删除公司");
			p26.setPermissionType(1);//权限类型
			p26.setPath("company-manage/delete");
			p26.setParentPermissionId(8);
			p26.setDeep(3);//从0开始
			p26.setFloor(2);//层级从1开始
			ls.add(p26);
			p27.setId(29);
			p27.setCode("company-manage/view");
			p27.setPermissionName("查看公司信息");
			p27.setPermissionType(1);//权限类型
			p27.setPath("company-manage/view");
			p27.setParentPermissionId(8);
			p27.setDeep(4);//从0开始
			p27.setFloor(2);//层级从1开始
			ls.add(p27);
			Collections.sort(ls,new Comparator<Permission>() {

				@Override
				public int compare(Permission o1, Permission o2) {
					// TODO Auto-generated method stub
					return o1.getId().compareTo(o2.getId());
				}
			});
		return ls;
	}
}
