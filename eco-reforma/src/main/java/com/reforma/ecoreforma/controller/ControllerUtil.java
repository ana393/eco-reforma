package com.reforma.ecoreforma.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.el.lang.FunctionMapperImpl.Function;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
/**
 *  Clase  con metodos utiles para las clases Controller.
 * 
 * La anotacion @Controller  sirve para informar a Spring que esta es una clase @Bean,
 * y que se debe cargar cuando se lanza la aplicacion.
 * 
 *@author Ana Tcaci
 *@version 1.0
 */
public class ControllerUtil {

	/**
	 * Devuelve erores validados a la pagina html.
	 * 
	 * @param bindingResult erores validados por http request
	 * @return la validacion de erores a la pagina html. 
	 */
	static Map<String, String> obtenerErrores(BindingResult bindingResult){
		Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
				fieldError -> fieldError.getField() + "Error", 
				FieldError::getDefaultMessage);
				
		return bindingResult.getFieldErrors().stream().collect(collector);
	}
	
	/**
	 * Devuelve paginacion computada.
	 * @param page es una sublista de la lista de objetos.
	 * @return paginacion computada.
	 */
	static int[] computePagination(Page page) {
        Integer totalPages = page.getTotalPages();
        if (totalPages > 4) {
            Integer pageNumber = page.getNumber() + 1;
            Integer[] head = pageNumber > 4 ? new Integer[]{1, -1} : new Integer[]{1, 2, 3};
            Integer[] tail = pageNumber < (totalPages - 3) ? new Integer[]{-1, totalPages} : new Integer[]{totalPages - 2, totalPages - 1, totalPages};
            Integer[] bodyBefore = (pageNumber > 4 && pageNumber < (totalPages - 1)) ? new Integer[]{pageNumber - 2, pageNumber - 1} : new Integer[]{};
            Integer[] bodyAfter = (pageNumber > 2 && pageNumber < (totalPages - 3)) ? new Integer[]{pageNumber + 1, pageNumber + 2} : new Integer[]{};

            List<Integer> list = new ArrayList<>();
            Collections.addAll(list, head);
            Collections.addAll(list, bodyBefore);
            Collections.addAll(list, (pageNumber > 3 && pageNumber < totalPages - 2) ? new Integer[]{pageNumber} : new Integer[]{});
            Collections.addAll(list, bodyAfter);
            Collections.addAll(list, tail);
            Integer[] arr = list.toArray(new Integer[0]);
            int[] res = Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
            return res;
        } else {
            return IntStream.rangeClosed(1, totalPages).toArray();
        }
    }

}
