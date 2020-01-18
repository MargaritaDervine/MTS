package com.start.mts.web;

import com.start.mts.db.EnvironmentRepository;
import com.start.mts.db.NameRepository;
import com.start.mts.domain.Environment;
import com.start.mts.domain.Name;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Controller
public class StaticDataController {
    @Autowired
    NameRepository nameRepository;

    @Autowired
    EnvironmentRepository environmentRepository;

    @RequestMapping(value = "/staticDataPage", method = RequestMethod.GET)
    public String get(Model model) {
        return "staticDataPage";
    }

    @RequestMapping(value = "/staticDataPage", method = RequestMethod.POST)
    public String addName(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "envName", required = false) String envName,
                          @RequestParam(value = "isReference", required = false) boolean isReference,
                          Model model) {

        if (StringUtils.isNotEmpty(name)) {
            Name nameSaved = nameRepository.save(new Name(name));
            if (StringUtils.isNotEmpty(nameSaved.getName())) {
                model.addAttribute("successName", true);
            } else  model.addAttribute("successName", false);
        }

        if (StringUtils.isNotEmpty(envName)) {
            Environment environment = new Environment(envName, isReference);
            Environment environmentSaved = environmentRepository.save(environment);
            if (isNotEmpty(environmentSaved.getEnvironmentName())) {
                model.addAttribute("successEnv", true);
            } else  model.addAttribute("successEnv", false);
        }

        return "staticDataPage";
    }
}
