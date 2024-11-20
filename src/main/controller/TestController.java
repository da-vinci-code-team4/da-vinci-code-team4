package main.controller;

import main.game.TestMainModel;
import main.view.TestMainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestController {
    private TestMainModel model;
    private TestMainFrame view;

    public TestController(TestMainModel model, TestMainFrame view) {
        this.model = model;
        this.view = view;
        initView();
    }

    private void initView() {
        // 초기 뷰 설정
        view.getLabel().setText(model.getData());
    }

    public void initController() {
        // 버튼 클릭 이벤트 처리
        view.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
                updateView();
            }
        });
    }

    private void updateModel() {
        // 모델 업데이트 로직
        model.setData("새로운 데이터");
    }

    private void updateView() {
        // 뷰 업데이트 로직
        view.getLabel().setText(model.getData());
    }
}
