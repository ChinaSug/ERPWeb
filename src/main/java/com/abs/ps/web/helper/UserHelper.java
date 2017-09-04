package com.abs.ps.web.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.domain.User;
import com.abs.ps.service.DepartmentService;
import com.abs.ps.service.OrgService;
import com.abs.ps.service.UserService;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.MenuItemDto;
import com.abs.ps.web.dto.NameCodeDto;
import com.abs.ps.web.dto.OrganizationDto;
import com.abs.ps.web.dto.UserDto;

public class UserHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(UserHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private int pageSize = 10;
	private UserDto sessionUserDto = null;
	
	private UserService userService = null;
	private DepartmentService departmentService = null;
	
	public UserHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context, int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize = pageSize;
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.userService = (UserService) factory.getBeanByName("userService");
		this.departmentService = (DepartmentService) factory.getBeanByName("departmentService");
		
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
	}
	
	
	public void doDelete() throws ServletException, IOException{
		String[] delIds = request.getParameterValues("delid");
		userService.deleteUserById(delIds);
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, 10);
		actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_DELETE, "QUEUE_USER", "", ""));
		doQuery();
		
		
	}
	
	private List<NameCodeDto> convertDtoByType(String userType) {
		List<NameCodeDto> ncList = new ArrayList<NameCodeDto>();
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		if (userType.equals("1")) {
			OrgService orgService = (OrgService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("orgService");
			
			List<OrganizationDto> orgDtoList = new ArrayList<OrganizationDto>();
			if (sessionUserDto != null && "ADMIN".equals(sessionUserDto.getUserId())) {
				orgDtoList = orgService.getAllOrganization();

			} else {
				OrganizationDto orgDto = orgService.getOrganizationById(sessionUserDto.getOrgOid());
				orgDtoList.add(orgDto);
			}
			
			for (OrganizationDto orgDto : orgDtoList) {
				NameCodeDto ncDto = new NameCodeDto();
				ncDto.setCode(String.valueOf(orgDto.getId()));
				ncDto.setName(orgDto.getOrgName());
				ncList.add(ncDto);
			}
		} else if (userType.equals("0")) {
//			EnterpriceInfoService enterpriceInfoService = (EnterpriceInfoService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("enterpriceInfoService");
//			List<EnterpriceInfoDto> entDtoList = enterpriceInfoService.findEntInfoByOrgOid(sessionUserDto.getOrgOid());
//			if (entDtoList != null) {
//				for (EnterpriceInfoDto entDto : entDtoList) {
//					NameCodeDto ncDto = new NameCodeDto();
//					ncDto.setCode(String.valueOf(entDto.getId()));
//					ncDto.setName(entDto.getEnterpriceName());
//					ncList.add(ncDto);
//				}
//			}
		}
		return ncList;
	}
	
	public void changeUserType() throws ServletException, IOException{
		String userType = request.getParameter("user_type");
		List<NameCodeDto> entDepartList =  convertDtoByType(userType);
		request.setAttribute(QueueConstants.QUEUE_CENTER_LIST, entDepartList);
		request.setAttribute("USER_TYPE_SELECTED", userType);
		
		UserDto userDto = new UserDto();
		userDto.setUserId(request.getParameter("userid"));	
		userDto.setUserName(StringHelper.getNewString(request.getParameter("username")));
		userDto.setPassword(request.getParameter("password"));
		userDto.setNickName(StringHelper.getNewString(request.getParameter("nickName")));
		userDto.setEmail(request.getParameter("email"));
		userDto.setMobileNum(request.getParameter("mobileNum"));
		request.setAttribute(QueueConstants.QUEUE_USER, userDto);
		
		PageDispatcher.forward(request, response, "jsp/user_maintain.jsp");
	}
	
	public void doSave() throws ServletException, IOException{
		UserDto userDto = new UserDto();
		User saveUser = null; // 保存后的用户
		boolean isNew = false;
		
		// 增加头像上传功能
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		String orgOid = String.valueOf(sessionUserDto.getOrgOid());
		
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		String relatePath = "IMG/" + sessionUserDto.getOrgOid();
		String basePathFolder = contextPath + relatePath;
		
		File folder = new File(basePathFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		InputStream in = null;
		FileOutputStream outStream = null;
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			if(!ServletFileUpload.isMultipartContent(request)){
				return;
			}
			upload.setHeaderEncoding("UTF-8");
			upload.setFileSizeMax(1024*1024*10);
			upload.setSizeMax(1024*1024*10);
			upload.setProgressListener(new ProgressListener(){
				public void update(long pBytesRead, long pContentLength, int arg2) {
					System.out.println("pContentLength " + pContentLength + ",pBytesRead " + pBytesRead);
				}
			});
			
			List<FileItem> list = upload.parseRequest(request);
			String imgToken = ""; //用于判断图片是否更改
			String oldImgPath = ""; //旧图片的地址，若有新图片更新，则删除旧的图片
			
			UserDto existUserDto = null;
			if (list != null) {
				int count = 0;
				// 判断是否是新用户且用户Id是否已存在与数据库
				for (FileItem isNewItem : list) {
					if (isNewItem.isFormField()) {
						String oid = StringHelper.getNewString(isNewItem.getString());
						if ("oid".equals(isNewItem.getFieldName())) {
							if (!StringHelper.isEmpty(oid) && !"-1".equals(oid)) {
								userDto.setId(Long.parseLong(oid));
								UserDto tempDto = userService.getUserById(Long.parseLong(oid));
								if (tempDto != null) {
									oldImgPath = tempDto.getImgUrl();
								}
							} else {
								isNew = true;
							}
						} 
					}
				}
				for (FileItem isexistItem : list) {
					if (isexistItem.isFormField()) {
						String userId = StringHelper.getNewString(isexistItem.getString());
						if ("userid".equals(isexistItem.getFieldName())) {
							if (!StringHelper.isEmpty(userId)) {
								userId = userId.toUpperCase();
								existUserDto = userService.getUserByUserId(userId);
								userDto.setUserId(userId);
							}
						} 
					}
				}
				
				if (existUserDto != null && isNew) {
					request.setAttribute(QueueConstants.QUEUE_MESSAGE, QueueConstants.MESSAGE_RECORD_DUPLICATE);
				} else {
					for (FileItem item : list) {
						if (item.isFormField()) {
							String name = item.getFieldName();
							String value = StringHelper.getNewString(item.getString());
							
							if ("username".equals(name)) {
								userDto.setUserName(value);
							} else if ("centerCode".equals(name)) {
								if (!StringHelper.isEmpty(value)) {
									userDto.setOrgOid(Long.parseLong(value));
								}
							} else if ("password".equals(name)) {
								userDto.setPassword(value);
							} else if ("status".equals(name)) {
								userDto.setStatus(value);
							} else if ("role".equals(name)) {
								userDto.setRoleType(value);
								boolean isAdmin = "1".equals(value)?true:false;
								userDto.setAdmin(isAdmin);
							} else if ("email".equals(name)) {
								userDto.setEmail(value);
							} else if ("mobileNum".equals(name)) {
								userDto.setMobileNum(value);
							} else if ("nickName".equals(name)) {
								userDto.setNickName(value);
							} else if ("ent_oid".equals(name)) {
								if (!StringHelper.isEmpty(value)) {
									userDto.setEntOid(Long.parseLong(value));
								}
							}else if ("sex".equals(name)) {
								userDto.setSex(value);
							} else if ("imgToken".equals(name)) {
								imgToken = value;
							}
							
						} else {
							
							String filename = item.getName(); // 文件上传的input的name
							if (StringHelper.isEmpty(filename)) continue;
							filename = filename.substring(filename.lastIndexOf("\\") + 1);

							in = item.getInputStream(); // 获取文件流
							byte[] data = readInputStream(in); // 读取流文件到byte[]数组中
							
							long time = (new Date()).getTime();
							String relateFileName = relatePath + "/" + time + count + getFileSufix(filename); // 文件名
							String filePath = contextPath +  relateFileName; // 文件要存储的路径 + 文件名
							File imageFile = new File(filePath); 
							outStream = new FileOutputStream(imageFile); 
							outStream.write(data); // 将文件流写入本地文件中
							userDto.setImgUrl(relateFileName);
							
							// 压缩图片大小，损坏的图片会报错IIOException
							if(imageFile.isFile()){
								Thumbnails.of(imageFile).scale(1f).outputQuality(0.25f).toFile(imageFile);
							}
							count++;
							item.delete();
						}
					}
					
					// 判断是否有旧的头像，若更新了新图片就删除掉旧的
					if(!StringHelper.isEmpty(imgToken)){
						userDto.setImgUrl(imgToken);
					}else{
						File oldImg = new File(contextPath+oldImgPath);
						if(oldImg.isFile()){
							oldImg.delete();
						}
					}
					
					// 默认新建用户所属单位为当前用户所属单位
					userDto.setOrgOid(sessionUserDto.getOrgOid());
					
					saveUser = userService.saveUser(userDto);
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (outStream != null) {
				outStream.close(); 
			}
		}
		
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, 10);
//DaoSupportTest.printJsonStr(saveUser);
		if (isNew && saveUser.getOid() > 0) {
			// 新建用户添加完毕跳到权限设置页面
			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_ADD, "QUEUE_USER", userDto.getUserId(), ""));
			request.setAttribute("MSG", "保存成功，请设置用户权限");
			PageDispatcher.forward(request, response, "mgHandler.html?op_action=USER_PRIV_PAGE&oid=" + saveUser.getOid());
		} else {
			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_MODIFY, "QUEUE_USER", userDto.getUserId(), ""));
			doQuery();
		}

	}
	
	private void initParams() {
		List<NameCodeDto> ncList = new ArrayList<NameCodeDto>();
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		request.setAttribute("DEFAULT_ORG", String.valueOf(sessionUserDto.getOrgOid()));
		request.setAttribute("DEFAULT_ADMIN", sessionUserDto.isAdmin()?"Y":"N");
		
		OrgService orgService = (OrgService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("orgService");
		List<OrganizationDto> orgDtoList = new ArrayList<OrganizationDto>();
		
		if (sessionUserDto.isAdmin()) {

			if (sessionUserDto != null && "ADMIN".equals(sessionUserDto.getUserId())) {
				orgDtoList = orgService.getAllOrganization();
	
			} else {
				OrganizationDto orgDto = orgService.getOrganizationById(sessionUserDto.getOrgOid());
				orgDtoList.add(orgDto);
			}

		} else {
			OrganizationDto orgDto = orgService.getOrganizationById(sessionUserDto.getOrgOid());
			orgDtoList.add(orgDto);
		}
		
		for (OrganizationDto orgDto : orgDtoList) {
			NameCodeDto ncDto = new NameCodeDto();
			ncDto.setCode(String.valueOf(orgDto.getId()));
			ncDto.setName(orgDto.getOrgName());
			ncList.add(ncDto);
		}
		
		request.setAttribute(QueueConstants.QUEUE_CENTER_LIST, ncList);
		request.setAttribute("SCOPE_LIST", departmentService.getDepartmentByOrg(sessionUserDto.getOrgOid()));
		
		
//		EnterpriceInfoService enterpriceInfoService = (EnterpriceInfoService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("enterpriceInfoService");
//		List<NameCodeDto> entNcList = new ArrayList<NameCodeDto>(); 
//		if (sessionUserDto.isAdmin()) {
//			List<EnterpriceInfoDto> entDtoList = enterpriceInfoService.findEntInfoByOrgOid(sessionUserDto.getOrgOid());			
//			if (entDtoList != null) {
//				for (EnterpriceInfoDto entDto : entDtoList) {
//					NameCodeDto ncDto = new NameCodeDto();
//					ncDto.setCode(String.valueOf(entDto.getId()));
//					ncDto.setName(entDto.getEnterpriceName());
//					entNcList.add(ncDto);
//				}
//			}
//		} else {
//			EnterpriceInfoDto entInfoDto = enterpriceInfoService.getObjectById(sessionUserDto.getEntOid());
//			NameCodeDto ncDto = new NameCodeDto();
//			ncDto.setCode(String.valueOf(entInfoDto.getId()));
//			ncDto.setName(entInfoDto.getEnterpriceName());
//			entNcList.add(ncDto);
//		}
//		request.setAttribute("ENT_LIST", entNcList);
	}
	
	public void doModify() throws ServletException, IOException{
		UserDto userDto = userService.getUserById(Long.valueOf(request.getParameter("oid")));
		request.setAttribute(QueueConstants.QUEUE_USER, userDto);
		request.setAttribute("IMG_MAX_SIZE", FilterUtil.getImgMaxSize(context));
		initParams();

		PageDispatcher.forward(request, response, "jsp/user_maintain.jsp");
	}
	
	public void doAdd() throws ServletException, IOException{
		request.setAttribute(QueueConstants.QUEUE_CENTER_LIST, new ArrayList<NameCodeDto>());
		request.setAttribute("IMG_MAX_SIZE", FilterUtil.getImgMaxSize(context));
		
		initParams();
		
		PageDispatcher.forward(request, response, "jsp/user_maintain.jsp");
	}
	
	public void doQuery() throws ServletException, IOException{
		logger.debug("doQueryUser()...");
		
		OrgService orgService = (OrgService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("orgService");
		String pageNumStr = request.getParameter("pageNumber");
		int pageNum = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		String centerCode = "";
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		if (sessionUserDto != null && !"ADMIN".equals(sessionUserDto.getUserId())) {
			centerCode = String.valueOf(sessionUserDto.getOrgOid());
		}
		Map<Long, OrganizationDto> orgMap = new HashMap<Long, OrganizationDto>();
//		IPaging paging = userService.findUserWithPaging(pageNum, pageSize, centerCode, sessionUserDto.getEntOid()>0?String.valueOf(sessionUserDto.getEntOid()):null);
		IPaging paging = userService.findUserWithPaging(pageNum, pageSize, centerCode, null);
		List<UserDto> newElements = new ArrayList<UserDto>();
		
		if (!paging.getThisPageElements().isEmpty()) {
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				User user = (User) paging.getThisPageElements().get(i);
				logger.debug("doQueryUser() user id " + user.getUserId());
				UserDto userDto = new UserDto();
				userDto.setId(user.getOid().longValue());
				userDto.setUserId(user.getUserId().toUpperCase());
				userDto.setUserName(user.getUserName());
				userDto.setAdmin(user.getIsAdmin().booleanValue());
				userDto.setOrgOid(user.getOrgOid());
				userDto.setEntOid(user.getEntOid() != null ? user.getEntOid() : -1);
				
				OrganizationDto orgDto = orgMap.get(user.getOrgOid());
				if (orgDto == null) {
					orgDto = orgService.getOrganizationById(user.getOrgOid());
					orgMap.put(user.getOrgOid(), orgDto);
				}
				if (orgDto != null) {
					userDto.setOrgName(orgDto.getOrgName());
				}
				userDto.setStatus(user.getStatus());
				if ("ADMIN".equals(userDto.getUserId())) {
					userDto.setDeletable(false);
				} else {
					userDto.setDeletable(userService.isUserDeletable(user.getOid()));
				}
				newElements.add(userDto);
			}
		}
		paging.refreshPageElements(newElements);
		paging.setURL(request.getContextPath() + "/mgHandler.html?op_action=USER_LIST");
		
		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		request.setAttribute("SCOPE_LIST", departmentService.getDepartmentByOrg(sessionUserDto.getOrgOid()));
		PageDispatcher.forward(request, response, "jsp/user_list.jsp");
	}
	
	public void loadPrivPage() throws ServletException, IOException {
		String userOid = request.getParameter("oid");
		if(!StringHelper.isEmpty(userOid)) {
			UserDto userDto = userService.getUserById(Long.valueOf(userOid));	
			if (userDto != null) {
				List<MenuItemDto> menuItemDtos = userService.findMenuItemList(userDto.isAdmin(),request.getContextPath());
				
				request.setAttribute(QueueConstants.QUEUE_USER_PRIV_LIST, excludeMenu(menuItemDtos,  userDto.isAdmin(), false));
				
				List<Long> privOids = userService.findUserPrivOidByUserId(Long.valueOf(userOid));
				request.setAttribute(QueueConstants.QUEUE_USER_PRIV, privOids);
				
			}
		}
		request.setAttribute("USER_OID", userOid);
		
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		if (userDto.isAdmin()) {
			request.setAttribute(QueueConstants.QUEUE_USER_ADMIN, Boolean.TRUE);
		}

		PageDispatcher.forward(request, response, "jsp/priv_list.jsp");
	}

	private List<MenuItemDto> excludeMenu(List<MenuItemDto> menuItemDtos, boolean isAdmin, boolean isEndPoint) {
		List<MenuItemDto> newMenuItemDtos = new ArrayList<MenuItemDto>();
		for (MenuItemDto menuItemDto : menuItemDtos) {
			if(isAdmin) { 
				if(!exclude4Admin().contains(menuItemDto.getMenuCode())) {
					newMenuItemDtos.add(menuItemDto);
				}
			} else if(isEndPoint) {
				if ("BIZ_FET".equals(menuItemDto.getMenuCode())) {
					newMenuItemDtos.add(menuItemDto);
					break;
				}
			} else {
				if (!"BIZ_FET".equals(menuItemDto.getMenuCode())) {
					newMenuItemDtos.add(menuItemDto);
				}
			}
		}
		
		return newMenuItemDtos;
	}
	
	private List<String> exclude4Admin() {
		List<String> list = new ArrayList<String>();
		list.add("BIZ_CAL");
		list.add("BIZ_FET");
		return list;
	}
	
	public void saveUserPriv() throws ServletException, IOException {
		String userOid = request.getParameter("userOid");
		String[] privids = request.getParameterValues("privid");
		
		userService.saveUserPriv(privids, userOid);
		
		doQuery();
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) { 
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	private String getFileSufix(String file) {
		return file.substring(file.indexOf("."), file.length());
	}

}
