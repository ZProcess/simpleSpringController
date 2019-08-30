package demo;

@Controller
@RequestMapping("/salary")
public class SalaryController {
    @RequestMapping("/getSalary.json")
    public Integer getSalary(@RequestParam("name") String name,
                             @RequestParam("experience") String experience){
        return 10000;
    }
}
