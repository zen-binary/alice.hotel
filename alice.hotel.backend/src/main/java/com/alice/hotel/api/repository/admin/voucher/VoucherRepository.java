package com.alice.hotel.api.repository.admin.voucher;
import com.alice.hotel.api.entity.admin.voucher.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface VoucherRepository extends JpaRepository<VoucherEntity, String> {
    List<VoucherEntity> findByDelYnOrderByCodeAsc(String delYn);
    Optional<VoucherEntity> findByCodeAndDelYn(String code, String delYn);
    Optional<VoucherEntity> findByVoucherIdAndDelYn(String id, String delYn);
}
