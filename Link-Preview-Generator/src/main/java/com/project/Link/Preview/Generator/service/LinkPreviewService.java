package com.project.Link.Preview.Generator.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.project.Link.Preview.Generator.model.LinkMetadata;

@Service
public class LinkPreviewService {
	
	@Cacheable(value = "previews", key = "#url")
	public LinkMetadata getLinkMetadata(String url) throws IOException {
		
		
		// Clean the URL by trimming quotes and extra whitespace
        url = cleanUrl(url);
        
		// Fetch the page HTML using Jsoup
		Document document = Jsoup.connect(url).get();
		
		// Extract title, description, and image using Open Graph meta tags and fallback to standard meta tags
		String title = extractMetaTag(document, "og:title", "title");
		String description = extractMetaTag(document, "og:description", "description");
		String imageUrl = extractMetaTag(document, "og:image", "image");
		
		// Create a LinkMetadata object
		LinkMetadata metadata = new LinkMetadata(url, title, description, imageUrl, LocalDateTime.now());
				
		// Return the metadata
		return metadata;
	}
	
	// Utility method to extract meta tags
    private String extractMetaTag(Document document, String ogTag, String fallbackTag) {
        Element metaTag = document.select("meta[property=" + ogTag + "]").first();
        if (metaTag != null) {
            return metaTag.attr("content");
        }

        metaTag = document.select("meta[name=" + fallbackTag + "]").first();
        if (metaTag != null) {
            return metaTag.attr("content");
        }
        
        return "";  // Return empty if not found
    }
    
    // Utility method to clean the URL (trim quotes and whitespace)
    private String cleanUrl(String url) {
        // Remove quotes if present around the URL
        return url.replaceAll("^\"|\"$", "").trim();
    }
   
}
