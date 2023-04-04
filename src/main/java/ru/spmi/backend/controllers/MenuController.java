package ru.spmi.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.dto.MenuDTO;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping("/get_menu")
    public ResponseEntity<?> getMenu() {
        System.out.println("in getMenu");
        var response = new ResponseEntity<>(new MenuDTO("hello", "a"), HttpStatus.OK);
        System.out.println(response);
        return response;
//        return new ResponseEntity<>(new MenuDTO("{\"menu\":[{\"menu_item\":\"Журналы\",\"link\":\"url_1\",\"sorted\":\"1\", \"level\":[{\"menu_item\":\"Журнал студентов\",\"link\":\"url_1_1\",\"sorted\":\"1\"} ,{\"menu_item\":\"Журнал групп\",\"link\":\"url_1_2\",\"sorted\":\"2\"} ,{\"menu_item\":\"Журнал групп ДПК\",\"link\":\"url_1_3\",\"sorted\":\"3\"} ,{\"menu_item\":\"Журнал соискателей\",\"link\":\"url_1_4\",\"sorted\":\"4\"} ,{\"menu_item\":\"Журнал отчетов\",\"link\":\"url_1_5\",\"sorted\":\"5\"}]} ,{\"menu_item\":\"Программы и стандарты\",\"link\":\"url_2\",\"sorted\":\"2\", \"level\":[{\"menu_item\":\"Учебные планы\",\"link\":\"url_2_1\",\"sorted\":\"1\"} ,{\"menu_item\":\"НАГРУЗКА\",\"link\":\"url_2_2\",\"sorted\":\"2\", \"level\":[{\"menu_item\":\"Выписка из рабочих учебных планов\",\"link\":\"url_3_1\",\"sorted\":\"1\"}]}]} ,{\"menu_item\":\"Диссертационный совет\",\"link\":\"url_4\",\"sorted\":\"4\"}]}"),
//                HttpStatus.OK);
    }
}
