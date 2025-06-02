package com.project.Link.Preview.Generator.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Link.Preview.Generator.model.LinkMetadata;
import com.project.Link.Preview.Generator.service.LinkPreviewService;

@RestController
@RequestMapping("/preview")
public class LinkPreviewController {
	
	@Autowired
	private LinkPreviewService linkPreviewService;
	
	@PostMapping
	public ResponseEntity<LinkMetadata> generatePreview(@RequestBody String url) {
		try {
			LinkMetadata metadata = linkPreviewService.getLinkMetadata(url);
			return ResponseEntity.ok(metadata);
		} catch (IOException e) {
			return ResponseEntity.status(500).body(null);  // Server error if something goes wrong with scraping
		}
	}
	
	@GetMapping("/{url}")
    public ResponseEntity<LinkMetadata> getCachedPreview(@PathVariable String url) {
        try {
            LinkMetadata metadata = linkPreviewService.getLinkMetadata(url);
            return ResponseEntity.ok(metadata);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);  // Server error
        }
    }

}
