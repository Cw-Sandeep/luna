if [ "$#" -ne 4 ]; then
    echo "I will ask questions!!"
else
  BUILD_TYPE=$1
  FLAVOUR_TYPE=$2
  BOARD_TYPE=$3
  INSTALL_SEL=$4
fi

echo "==== Build Type ===="
echo "1. Debug"
echo "2. Release"

while :
do
  if [ -z $BUILD_TYPE ]; then
    read -p "Select the build type:" BUILD_TYPE
  fi

  case $BUILD_TYPE in
	1)
		echo "Debug it is!!"
    BUILD=debug
    break
		;;
	2)
		echo "Release it is!!"
    BUILD=release
		break
		;;
	*)
		echo "Enter proper choice!."
    exit 0;
		;;
  esac
done


echo "==== Flavour Options ===="
echo "0. NONE"
echo "1. CLOUDWALKER"
echo "2. CVTE"
echo "3. VIDEOTEX"
echo "4. VEIRA"
echo "5. MEPL"
echo "6. GENERIC"
echo "7. AISEN"
echo "8. TOPTECH"
echo "9. DIXON"
echo "10. LITE"





while :
do
  if [ -z $FLAVOUR_TYPE ]; then
    read -p "Select the build you want to install:" FLAVOUR_TYPE
  fi
  case $FLAVOUR_TYPE in
  0)
    echo "Default !!"
    FLAVOUR=""
    break
    ;;
  1)
    echo "Cloudwalker it is!!"
    FLAVOUR=cloudwalker
    break
    ;;
  2)
    echo "Cvte it is!!"
    FLAVOUR=cvte
    break
    ;;
  3)
    echo "Videotex it is!!"
    FLAVOUR=videotex
    break
    ;;
  4)
    echo "Veira it is!!"
    FLAVOUR=veira
    break
    ;;
  5)
    echo "Mepl it is!!"
    FLAVOUR=mepl
    break
    ;;
  6)
    echo "Generic it is!!"
    FLAVOUR=generic
    break
    ;;
  7)
    echo "Aisen it is!!"
    FLAVOUR=aisen
    break
    ;;
  8)
    echo "Toptech !!"
    FLAVOUR=toptech
    break
    ;;
  9)
    echo "Dixon !!"
    FLAVOUR=dixon
    break
    ;;
  10)
    echo "Lite !!"
    FLAVOUR=lite
    break
    ;;
  *)
    echo "Sorry, I don't understand enter proper choice next time."
    exit 0;
    ;;
  esac
done




echo "==== Select mBoard ===="
echo "1. 338"
echo "2. 638"
echo "3. 5510"
echo "4. 358"
echo "5. 553"
echo "6. 708D"
echo "7. ATM30"
echo "8. Airstream A9"
echo "9. 905x"
echo "10. CWT_358"
echo "11. SK508"
echo "12. SK518"
echo "13. TT368"
echo "14. KNK810"


while :
do
  if [ -z $BOARD_TYPE ]; then
    read -p "Select the board you want to sign:" BOARD_TYPE
  fi
  case $BOARD_TYPE in
  1|2|3|4)
    echo "338/638/5510/358 it is !"
    SIGNPATH="$HOME/cloudwalker_signs/cvtx38_5510"
    BOARD=$(basename $SIGNPATH)
    break
    ;;
  5)
    echo "553 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/cvt553"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
  6)
    echo "708D it is!"
    SIGNPATH="$HOME/cloudwalker_signs/cvt708d"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
  7)
    echo "ATM30 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/cvtatm30"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
  8)
    echo "A9 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/a9_hibose_es_uv"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
  9)
    echo "905X it is!"
    SIGNPATH="$HOME/cloudwalker_signs/905x"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
  10)
    echo "CWT 358 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/cwt358"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
   11)
    echo "SK 508 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/sk508"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
   12)
    echo "SK 518 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/sk518"
    BOARD=$(basename $SIGNPATH)
    break
     ;;
   13)
    echo "TT 368 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/tt368"
    BOARD=$(basename $SIGNPATH)
    break
    ;;
   14)
    echo "KNK 810 it is!"
    SIGNPATH="$HOME/cloudwalker_signs/konka810"
    BOARD=$(basename $SIGNPATH)
    break
    ;;
   *)
     echo "Proper board name not selected!"
     exit 0;
     ;;
   esac
 done




APP=$PWD
RELPATH=$APP/app/build/outputs/apk/$FLAVOUR/$BUILD
rm -fv $RELPATH/*.apk
#$APP/gradlew -p $APP/ clean
$APP/gradlew -p $APP/ assemble$FLAVOUR$BUILD
CAPK=`ls -t $RELPATH/*.apk | head -n1` 
APK=$(basename "$CAPK")
bash $SIGNPATH/sign.sh $RELPATH/$APK

FILENAME=${APK%.*}
EXTENSION=${APK##*.}


echo "==== Select Installation TYpe ===="
echo "1. copy to sdcard"
echo "2. install signed"
echo "3. install unsigned"

say "Select Installation Type"

while :
do
  if [ -z $INSTALL_SEL ]; then
    read -p "Select the install type:" INSTALL_SEL
  fi
  case $INSTALL_SEL in
  1)
    echo "copying to sdcard.."
    #n=0;until [ "$n" -ge 5 ];do
    adb push dd"$RELPATH/${FILENAME}_${BOARD}_signed.${EXTENSION}" /sdcard/
    #n=$((n+1));sleep 5;done

    break
    ;;
  2)
    echo "signed apk installing.."
    #n=0;until [ "$n" -ge 5 ];do
    adb install -d -r -f "$RELPATH/${FILENAME}_${BOARD}_signed.${EXTENSION}"
    #n=$((n+1));sleep 5;done
    break
     ;;
  3)
    echo "unsigned apk installing.."
    #n=0;until [ "$n" -ge 5 ];do
    adb install -d -r -f "$RELPATH/$FILENAME.$EXTENSION"
    #n=$((n+1));sleep 5;done
    break
     ;;
   *)
     echo "Proper option not selected!"
     exit 0;
     ;;
   esac
 done

