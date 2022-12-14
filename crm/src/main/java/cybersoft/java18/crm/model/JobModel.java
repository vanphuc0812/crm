package cybersoft.java18.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

}
