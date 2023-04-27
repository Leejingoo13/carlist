import React, { useEffect, useState } from 'react';
import { SERVER_URL } from '../constants.js'; //서버 URL 가져오기
import { DataGrid, GridToolbarContainer, GridToolbarExport, gridClasses } from '@mui/x-data-grid'; // 데이터를 CSV 로 내보내는 기능(컴포넌트)
import Snackbar from '@mui/material/Snackbar';  // 삭제 결과를 보여주는 알림 메세지를 보여주기위한 MUI Snackbar 컴포넌트
import AddCar from './AddCar.js';
import EditCar from './EditCar.js';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';


// MUI GridToolbarContainer 및 GridToolbarExport 컴포넌트로 Export 버튼을 렌더링하는 toolbar 컴포넌트를 만든다.
function CustomToolbar() {
    return (
        <GridToolbarContainer
            ClassNames={gridClasses.GridToolbarContainer}>
            <GridToolbarExport/>
            </GridToolbarContainer>
    )
}

function Carlist() {
    const [cars, setCars] = useState([]); // REST API 에서 가져온 자동차 정보를 담을 상태 객체가 필요함
    const [open, setOpen] = useState(false);  // 삭제후에 표시되므로 이상태의 초기값은 false 여야 함

    useEffect(() => { 
        fetchCars();
    },[]);

    const fetchCars = () => { // 두번째 인수로 비어 있는 배열을 전달 하므로 fetch는 첫번째 렌더링 후에 한번만 실행됨
        // 세션 저장소에서 토큰을 읽고
        // Authorization 헤더에 이를 포함한다.
        const token = sessionStorage.getItem("jwt");
        
        fetch(SERVER_URL + 'api/cars', {
            headers: { 'Authorization' : token }
        }) // fetch 메서드에 가져온 상수를 이용
        .then(response => response.json()) // JSON 응답 데이터에 있는 자동차 데이터는 cars 상태에 저장됨
        .then(data => setCars(data._embedded.cars))
        .catch(err => console.error(err));
    }

    // onDelClick 함수 구현
    // useEffect 후크에서 fetch 메서드를 가져와야함 그 이유는 자동차를 삭제한 후 업데이트된 자동차 목록을 사용자에게 보여주려면 fetch를 호출해야하기 떄문
    // fetchCars라는 새 함수를 만들고 useEffect 후크에서 새 함수로 코드를 복사함
    // useEffect 후크에서 fetchCars함수를 호출해 자동차를 가져옴 
    const onDelClick = (url) => {
        if (window.confirm("Are you sure to delete?")){//Delete 버튼을 눌렀을 때 확인 대화상자를 표시하는 기능, Window 객체의 confirm 메서드로 이기능을 구현 할 수 있음
            const token = sessionStorage.getItem("jwt");

            fetch(url, {
                method:'DELETE',
                headers: { 'Authorization' : token } // CRUD 기능도 Authorization 헤더에 세션저장소에 토큰을 읽고 포함시킴 
            })
            .then(resopnse => {
                if (resopnse.ok) { // 삭제 작업이 정상적으로 진행됐는지 알아보기 위해 응답 상태를 확인 
                    fetchCars();
                    setOpen(true);
                }
                else {
                    alert('Something went wrong!');
                }
            })
            .catch(err => console.error(err))

        }
    }
    // Add a new car 
    // 백엔드 api/cars 엔드포인트에 POST 요청을 보낼 Carlist.js 파일에 addCar 함수를 구현
    // 요청은 본문에 새로운 자동차 객체와 'Content-Type':'application/json' 헤더를 포함
    // 자동차 객체는 JSON.stringify() 메서드를 이용해 JSON 문자열로 변환되므로 헤더가 필요함
    const addCar = (car) => {
        const token = sessionStorage.getItem("jwt");

        fetch(SERVER_URL + 'api/cars',
        {
            method: 'POST' ,
            headers: { 
                'Content-Type':'application/json',
                'Authorization' : token 
            },
            body: JSON.stringify(car)
        })
        .then(response => {
            if (response.ok) {
                fetchCars();
            }
            else {
                alert('Something went wrong!');
            }
        })
        .catch(err => console.error(err))
    }

    //update exting car
    const updateCar = (car, link) => {
        const token = sessionStorage.getItem("jwt");
        
        fetch(link,
            {
                method:'PUT',
                headers: { 
                    'Content-Type': 'application/json',
                    'Authorization' : token 
                },
                body: JSON.stringify(car)
            })
            .then(resopnse => {
                if (resopnse.ok) {
                    fetchCars();
                }
                else {
                    alert('Something went wrong! ');
                }
            })
            .catch(err => console.error(err))
    }
/*    return( //return 문에서 map 함수로 자동차 객체를 표행으로 변환하고 table
        <div>
            <table>
                <tbody>
                    {
                        cars.map((car, index) =>
                        <tr key={index}>
                            <td>{car.brand}</td>
                            <td>{car.model}</td>
                            <td>{car.color}</td>
                            <td>{car.year}</td>
                            <td>{car.price}</td>
                        </tr>)
                    }
                </tbody>
            </table>
       </div>
    ); */
    // 데이터 표의 열도 정의해아함 여기에는 field 는 자동차 객체의 속성 / 열의 제목은 headerName 속성으로 설정 할 수 있음. 열의 너비도 설정함
    // 함수에 전달되는 row 인수는 한 행의 모든 값이 들어 있는 행 단위 객체
    // 링크는 행의 id에 속성에 있으며, 이값을 delete 함수에 전달함
    // button 열에는 정렬과 필터링이 필요 없으므로 filterable과 sortable 프롭은 false로 설정
    // 버튼을 누르면 onDelclick 함수를 호출하고 링크 (row,id) 인수로 전달함 
    
    // 업데이트에 필요한 링크와 자동차 데이터를 포함하는 랭객체가 되는 row
    // EditCar 컴포넌트에서 변경 사항을 저장하기 위해 호출해야하는 updateCar 함수
    const columns = [
        {field: 'brand', headerName: 'Brand' , width:200},
        {field: 'model', headerName: 'Model' , width:200},
        {field: 'color', headerName: 'Color' , width:200},
        {field: 'year', headerName: 'Year' , width:150},
        {field: 'price', headerName: 'Price' , width:150},
        {
            field: '_links.car.href',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row =>
                <EditCar
                    data={row}
                    updateCar={updateCar} />
        },
        {
            field: '_links.self.herf',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row =>
                <IconButton onClick={() => onDelClick(row.id)}>
                    <DeleteIcon color="error" />
                </IconButton>
        }
    ];
    // Add a new car 
    // 백엔드 api/cars 엔드포인트에 POST 요청을 보낼 Carlist.js 파일에 addCar 함수를 구현
    // 요청은 본문에 새로운 자동차 객체와 'Content-Type':'application/json' 헤더를 포함
    // 자동차 객체는 JSON.stringify() 메서드를 이용해 JSON 문자열로 변환되므로 헤더가 필요함

// AddCar 컴포넌트를 추가하고 addCar 함수를 프롭으로서 AddCar 컴포넌트에 전달함 그러면 AddCar 컴포넌트에서 이 함수를 호출할 수 있게 됨
    return(
        <React.Fragment> 
            <Stack mt={2} mb={2}>
                <AddCar addCar={addCar} />
            </Stack>   
            <div style={{ height: 500, width: '100%' }}>
                <DataGrid
                    rows={cars}//함수에 전달되는 row 인수는 한 행의 모든 값이 들어 있는 행 단위 객체이다.
                    columns={columns}
                    disableRowSelectionOnClick={true}
                    getRowId={row => row._links.self.href}
                    components={{ Toolbar: CustomToolbar}} // Export 버튼을 포함하는 툴바를 활성화해야함 , MUI 데이터 표의 툴바를 활성화하려면 components 프롭으로 값을 Toolbar: CustomToolbar로 설정해야함
                    />

                <Snackbar
                    open={open}
                    autoHideDuration={2000} // onColse 함수가 자동으로 호출되고 메시지가 사라지는 시간을 밀리초 단위로 정의
                    onClose={() => setOpen(false)}
                    message="Car deleted"
                    />
            </div>
        </React.Fragment>
    );

}

export default Carlist;