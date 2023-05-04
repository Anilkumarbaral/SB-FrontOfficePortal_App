package ins.ashokit.service;

import java.util.List;

import ins.ashokit.Entity.StudentEnqEntity;
import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;

public interface EnquiryService {
	
   public List<String> getCourseName();
   
   public List<String> getEnqStatus();

   public DashboardResponse getDashboardData(Integer userid);
   
   public boolean upsertEnquiry(EnquiryForm form);
   
   public List<StudentEnqEntity> getEnquiries(EnquirySearchCriteria criteria );
   
   public List<StudentEnqEntity> getEnquiry();
}
