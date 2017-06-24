
package com.aleksus.handtohand1;

import com.backendless.BackendlessUser;

public class ExampleUser extends BackendlessUser
{
  public String getEmail() { return super.getEmail();  }

  public void setEmail( String email )
  {
    super.setEmail( email );
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public java.util.Date getCreated()
  {
    return (java.util.Date) super.getProperty( "created" );
  }

  public void setCreated( java.util.Date created )
  {
    super.setProperty( "created", created );
  }

  public String getName()
  {
    return (String) super.getProperty( "name" );
  }

  public void setName( String name )
  {
    super.setProperty( "name", name );
  }

  public String getOwnerId()
  {
    return (String) super.getProperty( "ownerId" );
  }

  public void setOwnerId( String ownerId )
  {
    super.setProperty( "ownerId", ownerId );
  }

  public String getObjectId()
  {
    return (String) super.getProperty( "objectId" );
  }

  public void setObjectId( String objectId )
  {
    super.setProperty( "objectId", objectId );
  }

  public java.util.Date getUpdated()
  {
    return (java.util.Date) super.getProperty( "updated" );
  }

  public void setUpdated( java.util.Date updated )
  {
    super.setProperty( "updated", updated );
  }
}