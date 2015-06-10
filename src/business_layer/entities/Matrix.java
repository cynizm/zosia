/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.entities;

import java.util.List;

/**
 *
 * @author Krzysiek
 */
public class Matrix {
    
     public static Object[][] matrix(List<Utility> lista) {
	Object matrix[][] = new Object[lista.size()][];
	int i = 0;
	for (Utility stan : lista) {
	    matrix[i++] = stan.toStringArray();
	}
	return matrix;
    }

    public static <T> T find(List<T> list, T data) {
	int idx = list.indexOf(data);
	if (idx != -1) {
	    return list.get(idx);
	}
	return null;
    }

}
