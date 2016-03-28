package dao;

import java.io.Serializable;

import controller.MyConnection;

public abstract class AbstractDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4177893537957791663L;
	MyConnection con;

	/**
	 * @param con
	 */
	public AbstractDAO(MyConnection con) {
		super();
		this.con = con;
	}
	
	
}
