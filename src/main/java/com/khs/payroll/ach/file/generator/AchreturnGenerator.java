package com.khs.payroll.ach.file.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.khs.payroll.domain.PaymentBatch;
import com.khs.payroll.payment.converter.RecordConverter;

@Component
public class AchreturnGenerator {

    // return type based on return code; on return type... BOC, REturn, etc
    private RecordConverter veronverter;

    public AchreturnGenerator(final RecordConverter veronverter) {
        this.veronverter = veronverter;
    }

    public void generateFile(final List<PaymentBatch> batches) {
        // switch case to line type....
        List<String> lines = new ArrayList<>();
        batches.stream().forEach(b -> dolien(b, lines));

    }

    private void dolien(final PaymentBatch b, final List<String> lines) {

        lines.add("");
    }

}
