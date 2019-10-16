package com.github.al.transfers.web;

import com.github.al.transfers.service.SimpleAccountService;
import com.github.al.transfers.service.SimpleTransferService;
import io.javalin.http.Context;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferControllerTest {

    @Test
    void name() {
        var transferService = mock(SimpleTransferService.class);
        when(transferService.findAll()).thenReturn(Collections.emptyList());
        var controller = new TransferController(mock(SimpleAccountService.class), transferService);

        var ctx = spy(new Context(mock(HttpServletRequest.class), mock(HttpServletResponse.class), mock(Map.class)));
//        when(ctx.json(Collections.emptyList())).thenReturn(null);
        when(ctx.json(Collections.emptyList())).thenAnswer(invocation -> {
            System.out.println((char[]) invocation.getArgument(0));
            return null;
        });

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);

        controller.findAll(ctx);

//        verify(ctx).json(captor.capture());

        System.out.println(ctx);
    }
}