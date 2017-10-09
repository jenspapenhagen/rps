package eu.papenhagen.rockpaperscissor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jens.papenhagen
 * @param <T>
 */
public class ReturnObject<T> {

    public final T object;
    public final T object2;

    public ReturnObject(T object, T object2) {
        this.object = object;
        this.object2 = object2;
    }
}
