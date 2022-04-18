package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import com.yjeom.pro01.memoryrestaurant.repository.MemoImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoImgService {

    @Value("${memoImgLocation}")
    private String memoImgLocation;

    private final MemoImgRepository memoImgRepository;

    private  final FileService fileService;

    public MemoImg uploadImg(MultipartFile memoImgFile)throws Exception{
        MemoImg memoImg=new MemoImg();
        String oriImgName=memoImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        if(!String.valueOf(oriImgName).isEmpty()){
            imgName=fileService.uploadFile(memoImgLocation,oriImgName,memoImgFile.getBytes());
            imgUrl="/images/img/memo/"+imgName;
        }
        memoImg.updateMemoImg(oriImgName,imgName,imgUrl);
        return memoImg;
    }
    public MemoImg saveMemoImg(MultipartFile memoImgFile)throws Exception{
        MemoImg memoImg=uploadImg(memoImgFile);
        memoImgRepository.save(memoImg);
        return memoImg;
    }
    public MemoImg updateMemoImg(PlaceMemo placeMemo, MultipartFile memoImgFile)throws Exception{
        if(memoImgFile !=null){
            MemoImg savedMemoImg;
            if(placeMemo.getMemoImg() !=null){
                Long memoImgId=placeMemo.getMemoImg().getId();
                savedMemoImg=memoImgRepository.findById(memoImgId).get();
                if(!savedMemoImg.getImgName().isEmpty()){
                    fileService.deleteFile(memoImgLocation+"/"+savedMemoImg.getImgName());
                }
                savedMemoImg=uploadImg(memoImgFile);
            }else{
                savedMemoImg=uploadImg(memoImgFile);
            }
            memoImgRepository.save(savedMemoImg);
            return savedMemoImg;
        }
        return null;
    }
    public void deleteMemoImg(PlaceMemo placeMemo) throws Exception {
        if (placeMemo.getMemoImg() != null) {
            Long memoImgId = placeMemo.getMemoImg().getId();
            MemoImg savedMemoImg = memoImgRepository.findById(memoImgId).get();
            if (!savedMemoImg.getImgName().isEmpty()) {
                fileService.deleteFile(memoImgLocation + "/" + savedMemoImg.getImgName());
            }
        }
    }
    public MemoImg getMemoImg(Long id){
        MemoImg memoImg=memoImgRepository.getById(id);
        return memoImg;
    }
}
