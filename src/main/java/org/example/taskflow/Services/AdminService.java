package org.example.taskflow.Services;

import org.example.taskflow.Models.Admin;
import org.example.taskflow.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin createAdmin(Admin admin) {
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse())); // ✅ encrypt password
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Admin existing = adminRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(updatedAdmin.getNom());
            existing.setPrenom(updatedAdmin.getPrenom());
            existing.setEmail(updatedAdmin.getEmail());

            // ✅ Only re-encode if password is changed
            if (!updatedAdmin.getMotDePasse().equals(existing.getMotDePasse())) {
                existing.setMotDePasse(passwordEncoder.encode(updatedAdmin.getMotDePasse()));
            }

            return adminRepository.save(existing);
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }




    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
