import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
  public class helloWorld {

    @RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hola mundo";
    }
  }
