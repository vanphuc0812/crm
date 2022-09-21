package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.repository.JobRepository;

import java.time.LocalDate;
import java.util.List;

public class JobService {
    private static JobService INSTANCE = new JobService();
    private JobRepository jobRepository = new JobRepository();

    public static JobService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JobService();
        }
        return INSTANCE;
    }

    public List<JobModel> getAllJobs() {
        return jobRepository.getAllJobs();
    }

    public int deleteJobById(String id) {
        return jobRepository.deleteJob(id);
    }

    public int updateJob(JobModel roleModel) {
        return jobRepository.updateJob(roleModel);
    }

    public int saveJob(String name, LocalDate startDate, LocalDate endDate) {
        return jobRepository.saveJob(name, startDate, endDate);
    }


    public JobModel getJobById(String id) {
        return jobRepository.getJobById(id);
    }
}
