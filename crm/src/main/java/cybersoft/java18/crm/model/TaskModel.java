package cybersoft.java18.crm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskModel {
    private int id;
    private String name;
    private int jobId;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int statusId;
}
