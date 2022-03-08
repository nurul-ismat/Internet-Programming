/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

/**
 *
 * @author U
 */
public class VCDBean
{
	String title;
	String actor;
  	float price;
  	int quantity;

  	public VCDBean()
  	{
    	title = "";
    	actor = "";
    	price = 0;
    	quantity = 0;
  	}

  	public void setTitle(String title)
  	{
    	this.title = title;
  	}

  	public void setActor(String actor)
  	{
    	this.actor = actor;
  	}

  	public void setPrice(float price)
  	{
    	this.price = price;
  	}

  	public void setQuantity(int quantity)
  	{
    	this.quantity = quantity;
  	}

  	//*******************************************
  	public String getTitle()
  	{
    	return title;
  	}

  	public String getActor()
  	{
    	return actor;
  	}

  	public float getPrice()
  	{
    	return price;
  	}

  	public int getQuantity()
  	{
    	return quantity;
  	}
}
