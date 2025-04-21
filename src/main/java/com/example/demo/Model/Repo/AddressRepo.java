package com.example.demo.Model.Repo;


import com.example.demo.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByUser_Id(Long id);
}
