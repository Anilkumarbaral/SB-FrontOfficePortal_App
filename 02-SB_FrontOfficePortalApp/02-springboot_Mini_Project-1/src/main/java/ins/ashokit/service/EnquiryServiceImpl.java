package ins.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.ashokit.Entity.CourseEntity;
import ins.ashokit.Entity.EnqStatusEntity;
import ins.ashokit.Entity.StudentEnqEntity;
import ins.ashokit.Entity.UserdtlsEntity;
import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;
import ins.ashokit.repo.CourseRepo;
import ins.ashokit.repo.EnqStatusRepo;
import ins.ashokit.repo.StudentEnqRepo;
import ins.ashokit.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnqRepo studentRepo;

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Autowired
	private UserDtlsRepo userRepo;

	@Override
	public List<String> getCourseName() {

		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();

		for (CourseEntity entity : findAll) {

			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnqStatusEntity> findAll = enqStatusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {

			statusList.add(entity.getStatusName());
		}

		return statusList;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userid) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserdtlsEntity> findById = userRepo.findById(userid);

		if (findById.isPresent()) {
			UserdtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			Integer totalEnquiriescnt = enquiries.size();

			Integer enrolledcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiries(totalEnquiriescnt);
			response.setEnrolledcnt(enrolledcnt);
			response.setLostcnt(lostcnt);

		}
		return response;
	}

	@Override
	public boolean upsertEnquiry(EnquiryForm form) {

		StudentEnqEntity Enqentity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, Enqentity);

		Integer userid = (Integer) session.getAttribute("userId");

		UserdtlsEntity userdtlsEntity = userRepo.findById(userid).get();
		Enqentity.setUser(userdtlsEntity);

		studentRepo.save(Enqentity);

		return true;
	}


	
	@Override
	public List<StudentEnqEntity> getEnquiry() {
		  Integer userid = (Integer)session.getAttribute("userId");		
		  Optional<UserdtlsEntity> findbyId = userRepo.findById(userid);
		  if(findbyId.isPresent()) {
			   UserdtlsEntity userdtlsEntity = findbyId.get();
			   List<StudentEnqEntity> enquiries = userdtlsEntity.getEnquiries();
			   return enquiries;
		  }
		return null;
	}
	
	
	
	

	@Override
	public List<StudentEnqEntity> getEnquiries(EnquirySearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
















