package com.yjeom.pro01.memoryrestaurant.service;

import antlr.StringUtils;
import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.repository.MemoImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoImgService {

    @Value("${memoImgLocation}")
    private String memoImgLocation;

    private final MemoImgRepository memoImgRepository;

    private  final FileService fileService;

    public void saveMemoImg(MemoImg memoImg, MultipartFile memoImgFile)throws Exception{
        String oriImgName=memoImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        if(!String.valueOf(oriImgName).isEmpty()){
            imgName=fileService.uploadFile(memoImgLocation,oriImgName,memoImgFile.getBytes());
            imgUrl="/images/memo/"+imgName;
        }
        memoImg.updateMemoImg(oriImgName,imgName,imgUrl);
        memoImgRepository.save(memoImg);
    }
}
