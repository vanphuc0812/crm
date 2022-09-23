package cybersoft.java18.crm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskModel {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int userId;
    private int jobId;
    private int statusId;
}
