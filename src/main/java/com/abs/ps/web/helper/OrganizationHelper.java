package com.abs.ps.web.helper;

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
import com.abs.ps.domain.Organization;
import com.abs.ps.service.OrgService;
import com.abs.ps.service.SupportingDataService;
import com.abs.ps.util.AMConstants;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.OrgDto;
import com.abs.ps.web.dto.OrganizationDto;
import com.abs.ps.web.dto.UserDto;

public class OrganizationHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(OrganizationHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private OrgService orgService = null;
	private int pageSize = 10;
	
	private SupportingDataService supportingDataService = null;
	
	public OrganizationHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context, int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize =pageSize;
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.orgService = (OrgService) factory.getBeanByName("orgService");
		this.supportingDataService = (SupportingDataService) factory.getBeanByName("supportingDataService");
	}
	
	public void doQuery() throws ServletException, IOException{
		logger.debug("doQuery()...");

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
			request.setAttribute("IS_ALLOW_ADD", "N");
		}
		
		IPaging paging = orgService.findOrgWithPaging(pageNum, pageSize, centerCode);
		List<OrganizationDto> newElements = new ArrayList<OrganizationDto>();
		if (!paging.getThisPageElements().isEmpty()) {
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				Organization center = (Organization) paging.getThisPageElements().get(i);
				OrganizationDto centerDto = new OrganizationDto();

				centerDto.setId(center.getOid().longValue());
				centerDto.setStatus(center.getStatus());
				centerDto.setOrgName(center.getOrgName());
				centerDto.setDeletable(orgService.deletable(center.getOid()));
				centerDto.setOrgCode(center.getOrgCode());
				centerDto.setNeedConfirm(center.getNeedConfirm() == null?false:center.getNeedConfirm().booleanValue());
				centerDto.setAssetsExpireAdvanceDateAmt(center.getAssetsExpireAdvanceDateAmt() == null?0:center.getAssetsExpireAdvanceDateAmt().intValue());
				centerDto.setReturnExpireAdvanceDateAmt(center.getReturnExpireAdvanceDateAmt() == null?0:center.getReturnExpireAdvanceDateAmt().intValue());
				
				newElements.add(centerDto);
			}
		}
		paging.refreshPageElements(newElements);
		paging.setURL(request.getContextPath() + "/mgHandler.html?op_action=ORG_LIST");
		
		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		
		PageDispatcher.forward(request, response, "jsp/org_list.jsp");
	}
	
	public void doDelete() throws ServletException, IOException{	
		String[] delIds = request.getParameterValues("delid");
		orgService.deleteObjectById(delIds);
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, 10);
		actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_DELETE, "ORG_DELETE", "", ""));
		doQuery();
	}
	
	public void doSave() throws ServletException, IOException{
		logger.debug("doSave()...");
		String userId = "";
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		if (sessionUserDto != null) {
			userId = sessionUserDto.getUserId();
		}

		OrgDto orgDto = new OrgDto();
		orgDto.setCreateBy(userId);

		String entDataTypes = "";
		try {
			
			String contextPath = request.getSession().getServletContext().getRealPath("/");
			String relatePath = "IMG/" + sessionUserDto.getOrgOid();
			String basePathFolder = contextPath + relatePath;
			
			File folder = new File(basePathFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024*100);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			upload.setFileSizeMax(1024*1024*10);
			upload.setSizeMax(1024*1024*10);
			upload.setProgressListener(new ProgressListener(){
				public void update(long pBytesRead, long pContentLength, int arg2) {
					System.out.println("pContentLength " + pContentLength + ",pBytesRead " + pBytesRead);
				}
			});
			if(!ServletFileUpload.isMultipartContent(request)){
				return;
			}

			List<FileItem> list = upload.parseRequest(request);
			
			// 用于判断图片是否被更改
			String logoToken = "";
			String rotateImgToken1 = "";
			String rotateImgToken2 = "";
			String rotateImgToken3 = "";
			String rotateImgToken4 = "";
			String rotateImgToken5 = "";
			
			String oldLogoPath = "";
			String oldRotateImgPath1 = "";
			String oldRotateImgPath2 = "";
			String oldRotateImgPath3 = "";
			String oldRotateImgPath4 = "";
			String oldRotateImgPath5 = "";
			
			Map<String, Object> valueMap = new HashMap<>();
			if (list != null) {
				int count = 0;
				for(FileItem item : list){
					if(!item.isFormField()){
						String filename = item.getName();
						
						if (StringHelper.isEmpty(filename)) continue;
						
						filename = filename.substring(filename.lastIndexOf("\\")+1);
						
						InputStream in = item.getInputStream();
						byte[] data = FilterUtil.readInputStream(in); 
						
						long time = (new Date()).getTime();
						String relateFileName = relatePath + "/" + time + count + FilterUtil.getFileSufix(filename);
						String filePath = contextPath +  relateFileName;
						File imageFile = new File(filePath); 
						
						FileOutputStream outStream = new FileOutputStream(imageFile); 
						outStream.write(data);
						outStream.close(); 
						
						if ("logoUrl".equals(item.getFieldName())) {
							orgDto.setLogoUrl(relateFileName);
						} else if ("rotateImgUrl1".equals(item.getFieldName())) {
							orgDto.setRotateImgUrl1(relateFileName);
						} else if ("rotateImgUrl2".equals(item.getFieldName())) {
							orgDto.setRotateImgUrl2(relateFileName);
						} else if ("rotateImgUrl3".equals(item.getFieldName())) {
							orgDto.setRotateImgUrl3(relateFileName);
						} else if ("rotateImgUrl4".equals(item.getFieldName())) {
							orgDto.setRotateImgUrl4(relateFileName);
						} else if ("rotateImgUrl5".equals(item.getFieldName())) {
							orgDto.setRotateImgUrl5(relateFileName);
						}

						count++;
						
						// 压缩图片大小
						if(imageFile.isFile()){
							Thumbnails.of(imageFile).scale(1f).outputQuality(0.25f).toFile(imageFile);
						}

						in.close();
						item.delete();
					} else {
						String fieldName = item.getFieldName();
						String value = StringHelper.getNewString(item.getString());
						
						valueMap.put(fieldName, value);
						
/*						if ("oid".equals(fieldName)) {
							orgDto.setOid(item.getString());
							if(!StringHelper.isEmpty(item.getString())){
								OrgDto oldDto = orgService.getOrgById(Long.valueOf(item.getString()));
								oldLogoPath = oldDto.getLogoUrl();
								oldRotateImgPath1 = oldDto.getRotateImgUrl1();
								oldRotateImgPath2 = oldDto.getRotateImgUrl2();
								oldRotateImgPath3 = oldDto.getRotateImgUrl3();
								oldRotateImgPath4 = oldDto.getRotateImgUrl4();
								oldRotateImgPath5 = oldDto.getRotateImgUrl5();
							}
						} else if ("centerName".equals(fieldName)) {
							orgDto.setOrgName(StringHelper.getNewString(item.getString()));
						} else if ("centerCode".equals(fieldName)) {
							orgDto.setOrgCode(StringHelper.getNewString(item.getString()));
						} else if ("descr".equals(fieldName)) {
							orgDto.setDescr(StringHelper.getNewString(item.getString()));
						} else if ("status".equals(fieldName)) {
							orgDto.setStatus(item.getString());
						} else if ("needConfirm".equals(fieldName)) {
							if ("1".equals(item.getString())) {
								orgDto.setNeedConfirm(true);
							} else {
								orgDto.setNeedConfirm(false);
							}
						} else if ("activityAmt".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setActivityAmt(Integer.parseInt(value));
							}
						} else if ("hotNoticeAmt".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setHotNoticeAmt(Integer.parseInt(value));
							}
						} else if ("hotActivityAmt".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setHotActivityAmt(Integer.parseInt(value));
							}
						} else if ("hotEnrollAmt".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setHotEnrollAmt(Integer.parseInt(value));
							}
						} else if ("logoToken".equals(fieldName)) { //判断图片是否更改
							if(!StringHelper.isEmpty(value)){
								logoToken = value;
							}
						} else if ("rotateToken1".equals(fieldName)) {
							if(!StringHelper.isEmpty(value)){
								rotateImgToken1 = value;
							}
						} else if ("rotateToken2".equals(fieldName)) {
							if(!StringHelper.isEmpty(value)){
								rotateImgToken2 = value;
							}
						} else if ("rotateToken3".equals(fieldName)) {
							if(!StringHelper.isEmpty(value)){
								rotateImgToken3 = value;
							}
						} else if ("rotateToken4".equals(fieldName)) {
							if(!StringHelper.isEmpty(value)){
								rotateImgToken4 = value;
							}
						} else if ("rotateToken5".equals(fieldName)) {
							if(!StringHelper.isEmpty(value)){
								rotateImgToken5 = value;
							}
						} else if ("entDataType".equals(fieldName)) {
							entDataTypes = item.getString();
						} else if ("imgForwardUrl1".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setImgForwardUrl1(value);
							}
						} else if ("imgForwardUrl2".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setImgForwardUrl2(value);
							}
						} else if ("imgForwardUrl3".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setImgForwardUrl3(value);
							}
						} else if ("imgForwardUrl4".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setImgForwardUrl4(value);
							}
						} else if ("imgForwardUrl5".equals(fieldName)) {
							if (!StringHelper.isEmpty(value)) {
								orgDto.setImgForwardUrl5(value);
							}
						}
*/					}
				}
				
				FilterUtil.setObjectField(orgDto, valueMap);
				
				//若图片未更改则保存原来的图片
				File oldImg = null;
				if (!StringHelper.isEmpty(logoToken)){
					orgDto.setLogoUrl(logoToken);
				} else {
					oldImg = new File(contextPath+oldLogoPath);
					if(oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				if(!StringHelper.isEmpty(rotateImgToken1)){
					orgDto.setRotateImgUrl1(rotateImgToken1);
				} else {
					oldImg = new File(contextPath+oldRotateImgPath1);
					if(oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				if (!StringHelper.isEmpty(rotateImgToken2)){
					orgDto.setRotateImgUrl2(rotateImgToken2);
				} else {
					oldImg = new File(contextPath+oldRotateImgPath2);
					if (oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				if (!StringHelper.isEmpty(rotateImgToken3)){
					orgDto.setRotateImgUrl3(rotateImgToken3);
				} else {
					oldImg = new File(contextPath+oldRotateImgPath3);
					if (oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				if (!StringHelper.isEmpty(rotateImgToken4)){
					orgDto.setRotateImgUrl4(rotateImgToken4);
				} else {
					oldImg = new File(contextPath+oldRotateImgPath4);
					if (oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				if(!StringHelper.isEmpty(rotateImgToken5)){
					orgDto.setRotateImgUrl5(rotateImgToken5);
				} else {
					oldImg = new File(contextPath+oldRotateImgPath5);
					if (oldImg.isFile()){
						oldImg.delete();
					}
				}
				
				
			}
			
		} catch (FileUploadBase.FileSizeLimitExceededException e)  {
			e.printStackTrace();
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}


		OrgService orgService = (OrgService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("orgService");
		if (StringHelper.isEmpty(orgDto.getOid()) && !orgService.isOrgCodeAvailabe(orgDto.getOrgCode())) {
			request.setAttribute("ERROR_CODE", "ORG_CODE_DUPLICATE");

			doModify();
		} else {
			Organization org = orgService.save(orgDto);
			if (org != null) {
				orgService.saveOrgDefineEntDataType(org.getOid(), entDataTypes.split(","));
			}
			
			ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, 10);
			if (!StringHelper.isEmpty(orgDto.getOid())) {
				actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_MODIFY, "ORG_SAVE", orgDto.getOrgName(), ""));
			} else {
				actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_ADD, "ORG_ADD", orgDto.getOrgName(), ""));
			}
			request.setAttribute("ERROR_CODE", AMConstants.AM_MESSAGE_SUCCESS);
			doQuery();
		}
	
	}
	public void doModify() throws ServletException, IOException{
		String oid = request.getParameter("oid");
		if (!StringHelper.isEmpty(oid)) {
			OrgDto orgDto = orgService.getOrgById(Long.valueOf(oid));
			request.setAttribute(QueueConstants.QUEUE_CENTER, orgDto);
		}
		/*request.setAttribute("ORG_DEFINE_ENT_DATA", orgService.getOrgDefineEntDataType(Long.valueOf(oid)));
		request.setAttribute(Constants.ENT_DATA_TYPE_LIST, supportingDataService.findEntDataTypeList());
		request.setAttribute("IMG_MAX_SIZE", getImgMaxSize(context));*/
		PageDispatcher.forward(request, response, "jsp/org_maintain.jsp");
	}
	public void doAdd() throws ServletException, IOException{
		/*request.setAttribute(Constants.ENT_DATA_TYPE_LIST, supportingDataService.findEntDataTypeList());
		request.setAttribute("IMG_MAX_SIZE", getImgMaxSize(context));
		PageDispatcher.forward(request, response, "jsp/org_maintain.jsp");*/
	}
	
	// 返回图片最大限制 默认10M
	private long getImgMaxSize(ServletContext con){
		if(con != null){
			Integer maxSize = (Integer)con.getAttribute("abs.img.max");
			if(maxSize != null && maxSize > 0){
				return maxSize*1024*1024;
			}
		}
		return 10*1024*1024;
	}
	
	private void setImgForwardUrl() {
		
	}
	
}
