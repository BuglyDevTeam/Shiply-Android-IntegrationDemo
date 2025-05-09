# 是否构建Release版本(true/false)
BUILD_RELEASE=true
moduleName="shiplyIntegrationDemo"

# 构建BaseApk
build_base_apk() {
  ./gradlew -s clean

  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :${moduleName}:assembleDebug -PRFIX_VERIFY_CASE='Base'
  else
    ./gradlew -s :${moduleName}:assembleRelease -PRFIX_VERIFY_CASE='Base'
  fi
}

# 构建PatchApk
build_patch_apk() {
  ./gradlew -s clean

  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :${moduleName}:assembleDebug -PRFIX_VERIFY_CASE='Patch'
  else
    ./gradlew -s :${moduleName}:assembleRelease -PRFIX_VERIFY_CASE='Patch'
  fi
}

# 暂停执行，按任意键继续
pause(){
  read -n 1 -p "Press any key to continue..." INP
  if [ "$INP" != '' ] ; then
    echo -ne '\b \n'
  fi
}

# 构建补丁
build_patch() {
  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :${moduleName}:RFixBuildDebug -PRFIX_PATCH_TYPE='Tinker'

    cp -f ${moduleName}/RFix/rfixPatch/debug/*_Tinker_signed.apk ${moduleName}/RFix/patch_tinker.apk
  else
    ./gradlew -s :${moduleName}:RFixBuildRelease -PRFIX_PATCH_TYPE='Tinker'

    cp -f ${moduleName}/RFix/rfixPatch/release/*_Tinker_signed.apk ${moduleName}/RFix/patch_tinker.apk
  fi

  # 返回成功，避免流水线执行出现失败
  return 0
}


chmod +x gradlew

# 1.清除构建缓存
rm -rf ${moduleName}/autoBackup
rm -rf ${moduleName}/RFix

mkdir ${moduleName}/autoBackup
mkdir ${moduleName}/RFix

# 2.构建基础APK
rm -rf ${moduleName}/autoBackup/*
build_base_apk

cp -f ${moduleName}/autoBackup/*-universal.apk ${moduleName}/RFix/old.apk
cp -f ${moduleName}/autoBackup/*-mapping.txt ${moduleName}/RFix/old_mapping.txt
cp -f ${moduleName}/autoBackup/*-R.txt ${moduleName}/RFix/old_R.txt

# 3.构建修复APK
# 暂停脚本(等待修改代码构造差异)
#pause

rm -rf ${moduleName}/autoBackup/*
build_patch_apk

cp -f ${moduleName}/autoBackup/*-universal.apk ${moduleName}/RFix/new.apk
cp -f ${moduleName}/autoBackup/*-mapping.txt ${moduleName}/RFix/new_mapping.txt

# 4.构建补丁包
rm -rf ${moduleName}/RFix/rfixPatch
build_patch

# 5.安装补丁包
#adb install ${moduleName}/RFix/old.apk
#adb push ${moduleName}/RFix/patch_tinker.apk /sdcard/RFix-patch.apk