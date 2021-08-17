package sg.edu.iss.jam.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jam.exceptions.StorageException;
import sg.edu.iss.jam.exceptions.StorageFileNotFoundException;



public class UploadImplementation implements UploadInterface {
	
	private Path fileStorageLocation;

	// @Autowired
	// mediarepo mediarepo;

	@Autowired
	public UploadImplementation() {
		this.fileStorageLocation = Paths.get("src\\main\\resources\\static\\media");
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file, String Mediatype) {

		if (Mediatype == "Audio") {
			this.fileStorageLocation= Paths.get(fileStorageLocation.toString(),"\\Audio");
		} else if (Mediatype == "Video") {
			this.fileStorageLocation= Paths.get(fileStorageLocation.toString(),"\\Videos");
		} else if (Mediatype =="thumbnail") {
			this.fileStorageLocation= Paths.get(fileStorageLocation.toString(),"\\Thumbnail");
		}
		
		
		
		System.out.println(this.fileStorageLocation);
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.fileStorageLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(this.fileStorageLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.fileStorageLocation, 1).filter(path -> !path.equals(this.fileStorageLocation))
					.map(this.fileStorageLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return fileStorageLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(fileStorageLocation.toFile());
	}

}
