create table SanPham (
    MaSP NUMBER GENERATED BY DEFAULT AS IDENTITY not null primary key,
    TenSP VARCHAR2(100),
    DvTinh VARCHAR2(20),
    GiaBan FLOAT
)

alter table SanPham add MauSac VARCHAR2(100)
alter table SanPham add KichThuocManHinh VARCHAR2(100)
alter table SanPham add Ram VARCHAR2(100)
alter table SanPham add BoNho VARCHAR2(100)
alter table SanPham add Pin VARCHAR2(100)
alter table SanPham add HeDieuHanh VARCHAR2(100)
alter table SanPham add SoLuongHang VARCHAR2(100)

create table NhaCungCap (
    MaNCC NUMBER GENERATED BY DEFAULT AS IDENTITY not null primary key,
    TenNCC VARCHAR2(200),
    DiaChi VARCHAR2(200),
    DienThoai VARCHAR2(20)
)

create table PhieuNhap (
    MaPN NUMBER GENERATED BY DEFAULT AS IDENTITY not null primary key,
    NgayNhap DATE,
    MaNCC int,
    FOREIGN KEY(MaNCC) REFERENCES NhaCungCap(MaNCC)
)

create table CTPhieuNhap (
    MaPN Number NOT NULL,
    MaSP Number NOT NULL,
    SLNhap int,
    DGNhap float,
    primary key(MaPN, MaSP),
    FOREIGN KEY(MaPN) REFERENCES PhieuNhap(MaPN),
    FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP)
)

create table PhieuXuat (
    MaPX NUMBER GENERATED BY DEFAULT AS IDENTITY not null primary key,
    NgayXuat DATE,
    TenKH VARCHAR2(200)
)

create table CTPhieuXuat (
    MaPX NUMBER NOT NULL,
    MaSP NUMBER NOT NULL,
    SLXuat int,
    DGXuat float,
    primary key(MaPX, MaSP),
    FOREIGN KEY(MaPX) REFERENCES PhieuXuat(MaPX),
    FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP)
)

create table TaiKhoan (
    MaTK NUMBER GENERATED BY DEFAULT AS IDENTITY not null primary key,
    TenDN VARCHAR2(50),
    MatKhau VARCHAR2(50)
)



----------------------------------

select ctpx.MaSP, Sum(SLXuat) from CTPhieuXuat ctpx
group by ctpx.MaSP

SELECT sp.TenSP, Sum(ctpx.SLXuat) FROM CTPhieuXuat ctpx
INNER JOIN SanPham sp ON ctpx.MaSP = sp.MaSP GROUP BY sp.TenSP
ORDER BY Sum(ctpx.SLXuat) DESC FETCH FIRST 5 ROWS ONLY


select masp from sanpham

select Sum(DGXuat) from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp
where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP

select sp.masp from sanpham sp, ctphieunhap ct
where sp.masp = ct.masp and ct.mapn = 61;

select * from phieuxuat where ngayxuat BETWEEN TO_DATE('2023-10-08', 'YYYY-MM-DD')
                    AND TO_DATE('2024-08-03', 'YYYY-MM-DD')
                    
select Sum(DGXuat) from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp 
where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP and ngayxuat between TO_DATE('2023/10/8', 'YYYY-MM-DD') 
AND TO_DATE('2024/08/03', 'YYYY-MM-DD')



select * from nhacungcap where mancc = 0 or tenncc like '%Tes%';

select max(MaPX) from PhieuXuat

Select MaNCC from PhieuNhap where MaPN = 31;

update NhaCungCap set TenNCC = 'TestSua', DiaChi = 'TestDiaChi', DienThoai = 'TestDienThoai' where MaNCC = 35;

select pn.MaPN, sp.TenSP, ncc.TenNCC, ncc.DiaChi, ncc.DienThoai, pn.NgayNhap, ctpn.SLNhap, ctpn.DGNhap from PhieuNhap pn, NhaCungCap ncc, SanPham sp, CTPhieuNhap ctpn
where pn.MaPN = ctpn.MaPN and sp.MaSP = ctpn.MaSP and pn.MaNCC = ncc.MaNCC;

insert into PhieuNhap(NgayNhap, MaNCC) values(To_date('27/09/2023 23:32:49', 'DD/MM/YYYY hh24:mi:ss'), 1);

Select * from PhieuNhap where NgayNhap = To_date('27/09/2023 23:53:49', 'DD/MM/YYYY hh24:mi:ss');

Select MaSP, TenSP, GiaBan from SanPham order by MaSP desc fetch  first 10 rows only;

