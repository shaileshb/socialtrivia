package com.graphsfm.stservice.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.MediaClipService;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.data.MediaClip;
import com.graphsfm.stservice.data.Question;

/**
 * API to fetch a set of audio clips. e.g.
 * 
 * http://host:port/rest/audioclips?offset=0&limit=10 will return a list of 10
 * audio clip objects starting with offset 0.
 * 
 * @author meera
 */
@Path("/audioclips/")
public class MediaClipResource {
	
	private MediaClipService mediaClipService;
	
	@Inject
	public void setMediaClipService(MediaClipService mediaClipService) {
		this.mediaClipService = mediaClipService;
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public List<MediaClip> getMediaClip(
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("limit") @DefaultValue("10") int limit) {
		List<MediaClip> medialist = mediaClipService.getMediaClip(offset, limit);
		return medialist;
	}
	
	@PUT
	@Produces({ "application/json", "application/xml" })
	public void addMediaClip(
			@FormParam("location") String location,
			@FormParam("artistName") String artistName) {
		mediaClipService.addMediaClip(location, artistName);
	}

}
