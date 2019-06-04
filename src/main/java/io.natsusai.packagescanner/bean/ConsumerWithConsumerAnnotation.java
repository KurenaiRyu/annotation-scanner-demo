package io.natsusai.packagescanner.bean;

import io.natsusai.packagescanner.annotation.Consumer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liufuhong
 * @since 2019-06-04 9:36
 */

@Consumer
@Controller
@RequestMapping("consumer")
public class ConsumerWithConsumerAnnotation {

  @RequestMapping(value = "/requestMapping", method = RequestMethod.GET)
  public void requestMapping() {

  }

  @GetMapping("/getMapping")
  public void getMapping() {

  }

  @PostMapping("/postMapping")
  public void postMapping() {

  }


}
