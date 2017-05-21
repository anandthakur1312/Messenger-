package com.anand.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anand.messenger.model.Profile;
import com.anand.messenger.service.ProfileService;


@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {
	
	ProfileService profileService = new ProfileService();

    @GET
    public List<Profile> getProfiles() {
    	
    	return profileService.getAllProfiles(); 
    }
    
    
    @GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
    	
    	Profile profile = profileService.getProfile(profileName);
		return profile;
    }
    
    @POST
	public Profile addProfile(Profile profile) {
    	
		Profile newProfile = profileService.addProfile(profile);
		return newProfile;
    }
    
    @PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
    	profile.setProfileName(profileName);
		Profile updatedProfile = profileService.updateProfile(profile);
		return updatedProfile;
    }
    
    @DELETE
	@Path("/{profileName}")
	public Profile deleteProfile(@PathParam("profileName") String profileName) {
    	
	return profileService.deleteProfile(profileName);
		
    }
     
}
